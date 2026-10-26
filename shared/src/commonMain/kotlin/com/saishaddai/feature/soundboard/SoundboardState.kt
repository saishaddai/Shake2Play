package com.saishaddai.feature.soundboard

data class SoundItem(
    val id: String,
    val title: String,
)

enum class PlaybackState {
    READY,
    PLAYING,
    FAILED,
}

data class SoundboardState(
    val sounds: List<SoundItem> = emptyList(),
    val selectedSoundId: String? = null,
    val playbackState: PlaybackState = PlaybackState.READY,
    val shakeAvailability: ShakeAvailability = ShakeAvailability.UNKNOWN,
) {
    val isEmpty: Boolean
        get() = sounds.isEmpty()

    val selectedSound: SoundItem?
        get() = selectedSoundId?.let { id ->
            sounds.firstOrNull { it.id == id }
        }

    val canShake: Boolean
        get() = selectedSoundId != null && !isEmpty &&
            shakeAvailability == ShakeAvailability.AVAILABLE && playbackState != PlaybackState.PLAYING

    val canPlayManually: Boolean
        get() = selectedSoundId != null && !isEmpty &&
            shakeAvailability == ShakeAvailability.UNAVAILABLE && playbackState != PlaybackState.PLAYING

    fun selectSound(soundId: String): SoundboardState {
        return if (sounds.any { it.id == soundId }) {
            copy(selectedSoundId = soundId, playbackState = PlaybackState.READY)
        } else {
            this
        }
    }

    fun handleShake(): SoundboardState {
        if (!canShake) {
            return this
        }

        return copy(playbackState = PlaybackState.PLAYING)
    }

    fun handleManualPlay(): SoundboardState {
        if (!canPlayManually) {
            return this
        }

        return copy(playbackState = PlaybackState.PLAYING)
    }

    fun onPlaybackFinished(): SoundboardState {
        return if (selectedSoundId == null) {
            this
        } else {
            copy(playbackState = PlaybackState.READY)
        }
    }

    fun onPlaybackFailed(): SoundboardState {
        return if (selectedSoundId == null) {
            this
        } else {
            copy(playbackState = PlaybackState.FAILED)
        }
    }
}