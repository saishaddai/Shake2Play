package com.saishaddai

import com.saishaddai.feature.soundboard.PlaybackState
import com.saishaddai.feature.soundboard.SoundItem
import com.saishaddai.feature.soundboard.SoundboardState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SoundboardStateTest {

    @Test
    fun emptySoundboardReportsEmpty() {
        assertTrue(SoundboardState().isEmpty)
    }

    @Test
    fun populatedSoundboardReportsItsSounds() {
        val sound = SoundItem(id = "bell", title = "Bell")
        val state = SoundboardState(sounds = listOf(sound))

        assertFalse(state.isEmpty)
        assertEquals(listOf(sound), state.sounds)
    }

    @Test
    fun selectingASoundUpdatesTheActiveSelection() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val chime = SoundItem(id = "chime", title = "Chime")
        val state = SoundboardState(sounds = listOf(bell, chime))

        val updated = state.selectSound(chime.id)

        assertEquals(chime, updated.selectedSound)
        assertEquals(chime.id, updated.selectedSoundId)
    }

    @Test
    fun shakingAnEmptySoundboardDoesNotCrash() {
        val state = SoundboardState()

        val updated = state.handleShake()

        assertTrue(updated.isEmpty)
        assertEquals(null, updated.selectedSound)
    }

    @Test
    fun selectedSoundStaysTheSameUntilPlaybackFinishes() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val chime = SoundItem(id = "chime", title = "Chime")
        val state = SoundboardState(sounds = listOf(bell, chime), selectedSoundId = bell.id)

        val playing = state.handleShake()
        val ignored = playing.handleShake()

        assertEquals(bell, playing.selectedSound)
        assertEquals(PlaybackState.PLAYING, playing.playbackState)
        assertEquals(bell, ignored.selectedSound)
        assertEquals(PlaybackState.PLAYING, ignored.playbackState)
    }

    @Test
    fun selectedSoundStartsPlaybackOnceWhenReady() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val state = SoundboardState(sounds = listOf(bell), selectedSoundId = bell.id)

        val playing = state.handleShake()

        assertEquals(PlaybackState.PLAYING, playing.playbackState)
        assertEquals(bell, playing.selectedSound)
    }

    @Test
    fun additionalShakesAreIgnoredWhileSoundIsPlaying() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val state = SoundboardState(sounds = listOf(bell), selectedSoundId = bell.id)

        val playing = state.handleShake()
        val ignored = playing.handleShake()

        assertEquals(PlaybackState.PLAYING, ignored.playbackState)
        assertEquals(bell, ignored.selectedSound)
    }

    @Test
    fun playbackFinishedResetsTheFlowForTheNextShake() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val state = SoundboardState(sounds = listOf(bell), selectedSoundId = bell.id)

        val playing = state.handleShake()
        val ready = playing.onPlaybackFinished()

        assertEquals(PlaybackState.READY, ready.playbackState)
        assertEquals(bell, ready.selectedSound)
    }
}