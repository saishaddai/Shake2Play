package com.saishaddai

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
    fun shakingMovesSelectionToTheNextSoundAndWrapsAround() {
        val bell = SoundItem(id = "bell", title = "Bell")
        val chime = SoundItem(id = "chime", title = "Chime")
        val cymbal = SoundItem(id = "cymbal", title = "Cymbal")
        val state = SoundboardState(sounds = listOf(bell, chime, cymbal), selectedSoundId = bell.id)

        val next = state.handleShake()
        val wrapped = next.handleShake()

        assertEquals(chime, next.selectedSound)
        assertEquals(cymbal, wrapped.selectedSound)
    }
}