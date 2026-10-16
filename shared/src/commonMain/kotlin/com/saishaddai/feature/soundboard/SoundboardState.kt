package com.saishaddai.feature.soundboard

data class SoundItem(
    val id: String,
    val title: String,
)

data class SoundboardState(
    val sounds: List<SoundItem> = emptyList(),
) {
    val isEmpty: Boolean
        get() = sounds.isEmpty()
}