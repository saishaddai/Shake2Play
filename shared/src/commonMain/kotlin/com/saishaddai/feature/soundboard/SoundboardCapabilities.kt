package com.saishaddai.feature.soundboard

fun soundResourcePath(soundId: String): String? {
    val resourceName = when (soundId) {
        "ahh", "explosion", "nyan", "oneechan", "tuturu" -> soundId
        else -> return null
    }
    return "files/sounds/$resourceName.m4a"
}

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
    LIFECYCLE_UNAVAILABLE,
    UNKNOWN,
}

interface SoundPlayer {
    suspend fun play(soundId: String): PlaybackResult
    fun stop()
}