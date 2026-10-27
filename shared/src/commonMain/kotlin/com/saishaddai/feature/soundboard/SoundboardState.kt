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
    val playbackFailure: PlaybackFailure? = null,
    val shakeAvailability: ShakeAvailability = ShakeAvailability.UNKNOWN,
    val isListening: Boolean = false,
) {
    val isEmpty: Boolean
        get() = sounds.isEmpty()

    val selectedSound: SoundItem?
        get() = selectedSoundId?.let { id ->
            sounds.firstOrNull { it.id == id }
        }

    val canShake: Boolean
        get() = selectedSoundId != null && !isEmpty && isListening &&
            shakeAvailability == ShakeAvailability.AVAILABLE && playbackState != PlaybackState.PLAYING

    val canPlayManually: Boolean
        get() = selectedSoundId != null && !isEmpty &&
            shakeAvailability == ShakeAvailability.UNAVAILABLE && playbackState != PlaybackState.PLAYING

    val canPreview: Boolean
        get() = selectedSoundId != null && !isEmpty && playbackState != PlaybackState.PLAYING

    fun selectSound(soundId: String): SoundboardState {
        return if (sounds.any { it.id == soundId }) {
            copy(
                selectedSoundId = soundId,
                playbackState = PlaybackState.READY,
                playbackFailure = null,
            )
        } else {
            this
        }
    }

    fun handleShake(): SoundboardState {
        if (!canShake) {
            return this
        }

        return copy(playbackState = PlaybackState.PLAYING, playbackFailure = null)
    }

    fun handleManualPlay(): SoundboardState {
        if (!canPlayManually) {
            return this
        }

        return copy(playbackState = PlaybackState.PLAYING, playbackFailure = null)
    }

    fun handlePreview(): SoundboardState {
        if (!canPreview) {
            return this
        }

        return copy(playbackState = PlaybackState.PLAYING, playbackFailure = null)
    }

    fun onPlaybackFinished(): SoundboardState {
        return if (selectedSoundId == null) {
            this
        } else {
            copy(playbackState = PlaybackState.READY, playbackFailure = null)
        }
    }

    fun onPlaybackFailed(
        failure: PlaybackFailure = PlaybackFailure.UNKNOWN,
    ): SoundboardState {
        return if (selectedSoundId == null) {
            this
        } else {
            copy(playbackState = PlaybackState.FAILED, playbackFailure = failure)
        }
    }
}