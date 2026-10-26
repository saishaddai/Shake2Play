package com.saishaddai

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.saishaddai.feature.soundboard.AndroidShakeDetector
import com.saishaddai.feature.soundboard.AndroidSoundPlayer
import com.saishaddai.feature.soundboard.PlaybackResult
import com.saishaddai.feature.soundboard.ShakeAvailability
import com.saishaddai.feature.soundboard.SoundItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var soundPlayer: AndroidSoundPlayer
    private lateinit var shakeDetector: AndroidShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        soundPlayer = AndroidSoundPlayer(this)
        shakeDetector = AndroidShakeDetector(this, this)

        setContent {
            App(
                sounds = listOf(
                    SoundItem("ahh", "Aah"),
                    SoundItem("explosion", "Explosion"),
                    SoundItem("nyan", "Nyan"),
                    SoundItem("oneechan", "Oneechan"),
                    SoundItem("tuturu", "Tuturu"),
                ),
                onPlaySound = { sound ->
                    lifecycleScope.launch {
                        when (val result = soundPlayer.play(sound.id)) {
                            PlaybackResult.Completed -> Unit
                            is PlaybackResult.Failed -> {
                                Log.e(TAG, "Unable to play sound: ${result.reason}")
                            }
                        }
                    }
                },
            )
        }
    }

    internal fun startShakeDetection(onShake: () -> Unit): ShakeAvailability =
        shakeDetector.start(onShake)

    internal fun stopShakeDetection() = shakeDetector.stop()

    override fun onDestroy() {
        stopShakeDetection()
        super.onDestroy()
    }

    private companion object {
        const val TAG = "Shake2Play"
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        sounds = listOf(
            SoundItem("ahh", "Aah"),
            SoundItem("explosion", "Explosion"),
            SoundItem("nyan", "Nyan"),
            SoundItem("oneechan", "Oneechan"),
            SoundItem("tuturu", "Tuturu"),
        ),
        onPlaySound = {},
    )
}