package com.saishaddai.feature.soundboard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class AndroidShakeDetector(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
) : ShakeDetector, DefaultLifecycleObserver, SensorEventListener {
    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var onShake: (() -> Unit)? = null
    private var isRegistered = false
    private var hasGravity = false
    private var gravityX = 0f
    private var gravityY = 0f
    private var gravityZ = 0f
    private var lastShakeAtMillis: Long? = null

    override fun start(onShake: () -> Unit): ShakeAvailability {
        stop()
        if (accelerometer == null ||
            lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED
        ) {
            return ShakeAvailability.UNAVAILABLE
        }

        this.onShake = onShake
        lifecycleOwner.lifecycle.addObserver(this)
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) &&
            !registerSensor()
        ) {
            stop()
            return ShakeAvailability.UNAVAILABLE
        }
        return ShakeAvailability.AVAILABLE
    }

    override fun stop() {
        unregisterSensor()
        onShake = null
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        registerSensor()
    }

    override fun onStop(owner: LifecycleOwner) {
        unregisterSensor()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stop()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val alpha = GRAVITY_FILTER_ALPHA
        if (!hasGravity) {
            gravityX = event.values[0]
            gravityY = event.values[1]
            gravityZ = event.values[2]
            hasGravity = true
            return
        }

        gravityX = alpha * gravityX + (1f - alpha) * event.values[0]
        gravityY = alpha * gravityY + (1f - alpha) * event.values[1]
        gravityZ = alpha * gravityZ + (1f - alpha) * event.values[2]

        val linearX = event.values[0] - gravityX
        val linearY = event.values[1] - gravityY
        val linearZ = event.values[2] - gravityZ
        val acceleration = kotlin.math.sqrt(
            linearX * linearX + linearY * linearY + linearZ * linearZ,
        )
        if (acceleration < SHAKE_THRESHOLD_METERS_PER_SECOND_SQUARED) return

        val now = SystemClock.elapsedRealtime()
        val previousShakeAt = lastShakeAtMillis
        if (previousShakeAt == null || now - previousShakeAt >= SHAKE_COOLDOWN_MILLIS) {
            lastShakeAtMillis = now
            onShake?.invoke()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    private fun registerSensor(): Boolean {
        if (isRegistered) return true
        val sensor = accelerometer ?: return false
        isRegistered = sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_UI,
        )
        if (isRegistered) {
            hasGravity = false
        }
        return isRegistered
    }

    private fun unregisterSensor() {
        if (!isRegistered) return
        sensorManager.unregisterListener(this)
        isRegistered = false
        hasGravity = false
    }

    private companion object {
        const val GRAVITY_FILTER_ALPHA = 0.8f
        const val SHAKE_THRESHOLD_METERS_PER_SECOND_SQUARED = 24.5f
        const val SHAKE_COOLDOWN_MILLIS = 500L
    }
}
