package com.saishaddai

import com.saishaddai.feature.soundboard.SoundItem
import com.saishaddai.feature.soundboard.filterSoundItems
import kotlin.test.Test
import kotlin.test.assertEquals

class SoundLibraryScreenTest {
    private val sounds = listOf(
        SoundItem("bell", "Bell"),
        SoundItem("chime", "Soft Chime"),
        SoundItem("alarm", "Alarm"),
    )

    @Test
    fun searchMatchesTitlesWithoutCaseSensitivity() {
        assertEquals(
            listOf(sounds[1]),
            filterSoundItems(sounds, "cHImE"),
        )
    }

    @Test
    fun blankSearchReturnsAllSoundsAndUnknownSearchReturnsNone() {
        assertEquals(sounds, filterSoundItems(sounds, "  "))
        assertEquals(emptyList(), filterSoundItems(sounds, "not found"))
    }
}
