package com.saishaddai

import com.saishaddai.feature.soundboard.PlaybackFailure
import com.saishaddai.feature.soundboard.PlaybackResult
import com.saishaddai.feature.soundboard.PlaybackState
import com.saishaddai.feature.soundboard.ShakeAvailability
import com.saishaddai.feature.soundboard.ShakeDetector
import com.saishaddai.feature.soundboard.SoundItem
import com.saishaddai.feature.soundboard.SoundPlayer
import com.saishaddai.feature.soundboard.SoundboardPresenter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SoundboardPresenterTest {
    @Test
    fun listeningShakePlaysTheSelectedSound() {
        val sound = SoundItem("bell", "Bell")
        val detector = FakeShakeDetector(ShakeAvailability.AVAILABLE)
        val player = FakeSoundPlayer()
        val presenter = SoundboardPresenter(
            sounds = listOf(sound),
            shakeDetector = detector,
            soundPlayer = player,
            scope = CoroutineScope(Dispatchers.Unconfined),
        )

        presenter.startListening()
        detector.triggerShake()

        assertTrue(presenter.state.isListening)
        assertEquals(listOf(sound.id), player.playedSoundIds)
        assertEquals(PlaybackState.READY, presenter.state.playbackState)
    }

    @Test
    fun unavailableDetectorLeavesPreviewAvailableAndPlaybackErrorsVisible() {
        val sound = SoundItem("bell", "Bell")
        val detector = FakeShakeDetector(ShakeAvailability.UNAVAILABLE)
        val player = FakeSoundPlayer(
            PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR),
        )
        val presenter = SoundboardPresenter(
            sounds = listOf(sound),
            shakeDetector = detector,
            soundPlayer = player,
            scope = CoroutineScope(Dispatchers.Unconfined),
        )

        presenter.startListening()
        presenter.previewSelectedSound()

        assertFalse(presenter.state.isListening)
        assertTrue(presenter.state.canPlayManually)
        assertEquals(PlaybackState.FAILED, presenter.state.playbackState)
        assertEquals(PlaybackFailure.DECODER_ERROR, presenter.state.playbackFailure)
    }

    @Test
    fun cancelledPlaybackDoesNotLeaveTheScreenInPlayingState() {
        val sound = SoundItem("bell", "Bell")
        val player = object : SoundPlayer {
            override suspend fun play(soundId: String): PlaybackResult {
                throw CancellationException("Playback was stopped")
            }

            override fun stop() = Unit
        }
        val presenter = SoundboardPresenter(
            sounds = listOf(sound),
            shakeDetector = null,
            soundPlayer = player,
            scope = CoroutineScope(Dispatchers.Unconfined),
        )

        presenter.previewSelectedSound()

        assertEquals(PlaybackState.READY, presenter.state.playbackState)
    }

    private class FakeShakeDetector(
        private val availability: ShakeAvailability,
    ) : ShakeDetector {
        private var onShake: (() -> Unit)? = null

        override fun start(onShake: () -> Unit): ShakeAvailability {
            this.onShake = onShake
            return availability
        }

        override fun stop() {
            onShake = null
        }

        fun triggerShake() {
            onShake?.invoke()
        }
    }

    private class FakeSoundPlayer(
        private val result: PlaybackResult = PlaybackResult.Completed,
    ) : SoundPlayer {
        val playedSoundIds = mutableListOf<String>()

        override suspend fun play(soundId: String): PlaybackResult {
            playedSoundIds += soundId
            return result
        }

        override fun stop() = Unit
    }
}
