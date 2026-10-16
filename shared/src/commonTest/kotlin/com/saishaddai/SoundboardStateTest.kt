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
}