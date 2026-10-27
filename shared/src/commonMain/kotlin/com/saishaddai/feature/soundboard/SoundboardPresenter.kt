package com.saishaddai.feature.soundboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SoundboardPresenter(
    sounds: List<SoundItem>,
    private val shakeDetector: ShakeDetector?,
    private val soundPlayer: SoundPlayer?,
    private val scope: CoroutineScope,
) {
    private var mutableState by mutableStateOf(
        SoundboardState(
            sounds = sounds,
            selectedSoundId = sounds.firstOrNull()?.id,
            shakeAvailability = if (shakeDetector == null) {
                ShakeAvailability.UNAVAILABLE
            } else {
                ShakeAvailability.UNKNOWN
            },
        ),
    )

    val state: SoundboardState
        get() = mutableState

    fun selectSound(soundId: String) {
        if (mutableState.playbackState != PlaybackState.PLAYING) {
            mutableState = mutableState.selectSound(soundId)
        }
    }

    fun startListening() {
        val detector = shakeDetector
        if (detector == null) {
            mutableState = mutableState.copy(
                shakeAvailability = ShakeAvailability.UNAVAILABLE,
                isListening = false,
            )
            return
        }

        val availability = detector.start {
            scope.launch {
                startPlayback(isShakeTriggered = true)
            }
        }
        mutableState = mutableState.copy(
            shakeAvailability = availability,
            isListening = availability == ShakeAvailability.AVAILABLE,
        )
    }

    fun stopListening() {
        shakeDetector?.stop()
        mutableState = mutableState.copy(isListening = false)
    }

    fun previewSelectedSound() {
        startPlayback(isShakeTriggered = false)
    }

    fun stop() {
        stopListening()
        soundPlayer?.stop()
    }

    private fun startPlayback(isShakeTriggered: Boolean) {
        val nextState = if (isShakeTriggered) {
            mutableState.handleShake()
        } else {
            mutableState.handlePreview()
        }
        if (nextState == mutableState) return

        mutableState = nextState
        val selectedSound = nextState.selectedSound ?: return
        val player = soundPlayer
        if (player == null) {
            mutableState = mutableState.onPlaybackFailed(PlaybackFailure.LIFECYCLE_UNAVAILABLE)
            return
        }

        scope.launch {
            try {
                when (val result = player.play(selectedSound.id)) {
                    PlaybackResult.Completed -> {
                        mutableState = mutableState.onPlaybackFinished()
                    }
                    is PlaybackResult.Failed -> {
                        mutableState = mutableState.onPlaybackFailed(result.reason)
                    }
                }
            } catch (cancellation: CancellationException) {
                mutableState = mutableState.onPlaybackFinished()
                throw cancellation
            } catch (_: Exception) {
                mutableState = mutableState.onPlaybackFailed(PlaybackFailure.UNKNOWN)
            }
        }
    }
}
