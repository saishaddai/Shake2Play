package com.saishaddai.feature.soundboard

data class SoundItem(
    val id: String,
    val title: String,
)

data class SoundboardState(
    val sounds: List<SoundItem> = emptyList(),
    val selectedSoundId: String? = null,
) {
    val isEmpty: Boolean
        get() = sounds.isEmpty()

    val selectedSound: SoundItem?
        get() = selectedSoundId?.let { id ->
            sounds.firstOrNull { it.id == id }
        }

    fun selectSound(soundId: String): SoundboardState {
        return if (sounds.any { it.id == soundId }) {
            copy(selectedSoundId = soundId)
        } else {
            this
        }
    }

    fun handleShake(): SoundboardState {
        if (sounds.isEmpty()) {
            return this
        }

        val currentIndex = selectedSoundId?.let { soundId ->
            sounds.indexOfFirst { it.id == soundId }
        }?.takeIf { it >= 0 } ?: 0

        val nextIndex = if (currentIndex == sounds.lastIndex) 0 else currentIndex + 1
        return copy(selectedSoundId = sounds[nextIndex].id)
    }
}