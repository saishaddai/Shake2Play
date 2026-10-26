package com.saishaddai.feature.soundboard

enum class ShakeAvailability {
    UNKNOWN,
    AVAILABLE,
    UNAVAILABLE,
}

interface ShakeDetector {
    fun start(onShake: () -> Unit): ShakeAvailability
    fun stop()
}

sealed interface PlaybackResult {
    data object Completed : PlaybackResult
    data class Failed(val reason: PlaybackFailure) : PlaybackResult
}

enum class PlaybackFailure {
    SOUND_NOT_FOUND,
    DECODER_ERROR,
    UNKNOWN,
}

interface SoundPlayer {
    suspend fun play(soundId: String): PlaybackResult
    fun stop()
}