package com.saishaddai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saishaddai.feature.soundboard.PlaybackFailure
import com.saishaddai.feature.soundboard.PlaybackState
import com.saishaddai.feature.soundboard.ShakeAvailability
import com.saishaddai.feature.soundboard.ShakeDetector
import com.saishaddai.feature.soundboard.SoundItem
import com.saishaddai.feature.soundboard.SoundPlayer
import com.saishaddai.feature.soundboard.SoundboardPresenter

@Composable
@Preview
fun App(
    sounds: List<SoundItem> = listOf(
        SoundItem("ahh", "Aah"),
        SoundItem("explosion", "Explosion"),
        SoundItem("nyan", "Nyan"),
        SoundItem("oneechan", "Oneechan"),
        SoundItem("tuturu", "Tuturu"),
    ),
    shakeDetector: ShakeDetector? = null,
    soundPlayer: SoundPlayer? = null,
) {
    val scope = rememberCoroutineScope()
    val presenter = remember(sounds, shakeDetector, soundPlayer, scope) {
        SoundboardPresenter(sounds, shakeDetector, soundPlayer, scope)
    }
    DisposableEffect(presenter) {
        onDispose(presenter::stop)
    }
    val state = presenter.state
    val selectedSound = state.selectedSound

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Text(
                    text = "Shake2Play",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "SOUNDBOARD",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = "Your sounds",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                if (state.isEmpty) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "No sounds yet",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = "Your sound library will appear here.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.sounds.forEach { sound ->
                            Button(
                                onClick = { presenter.selectSound(sound.id) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = state.playbackState != PlaybackState.PLAYING,
                                colors = if (sound.id == state.selectedSoundId) {
                                    ButtonDefaults.buttonColors()
                                } else {
                                    ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    )
                                },
                            ) {
                                Text(text = sound.title)
                            }
                        }
                    }
                }

                if (selectedSound != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(
                                text = "Selected",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            Text(
                                text = selectedSound.title,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            OutlinedButton(
                                onClick = presenter::previewSelectedSound,
                                modifier = Modifier.fillMaxWidth(),
                                enabled = state.canPreview && soundPlayer != null,
                            ) {
                                Text(
                                    text = if (state.playbackState == PlaybackState.PLAYING) {
                                        "Playing..."
                                    } else {
                                        "Preview sound"
                                    },
                                )
                            }
                            if (soundPlayer == null) {
                                Text(
                                    text = "Audio playback is not available on this platform.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            if (state.isListening) {
                                presenter.stopListening()
                            } else {
                                presenter.startListening()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = shakeDetector != null && !state.isEmpty,
                    ) {
                        Text(
                            text = when {
                                state.isListening -> "Stop listening"
                                state.shakeAvailability == ShakeAvailability.UNAVAILABLE ->
                                    "Try shake detection again"
                                else -> "Start listening"
                            },
                        )
                    }
                    Text(
                        text = listeningMessage(
                            state.shakeAvailability,
                            state.isListening,
                            shakeDetector != null,
                            selectedSound?.title,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                when (state.playbackState) {
                    PlaybackState.READY -> Unit
                    PlaybackState.PLAYING -> {
                        Text(
                            text = "Playing ${selectedSound?.title.orEmpty()}...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    PlaybackState.FAILED -> {
                        Text(
                            text = playbackErrorMessage(state.playbackFailure),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

private fun listeningMessage(
    availability: ShakeAvailability,
    isListening: Boolean,
    hasDetector: Boolean,
    selectedSoundTitle: String?,
): String = when {
    isListening && selectedSoundTitle != null ->
        "Shake detection is active. Shake to play $selectedSoundTitle."
    isListening -> "Shake detection is active."
    !hasDetector -> "Shake detection is not available on this platform."
    availability == ShakeAvailability.UNAVAILABLE ->
        "Shake detection is unavailable. You can still preview sounds."
    availability == ShakeAvailability.AVAILABLE -> "Shake detection is off."
    else -> "Start listening to enable shake-to-play."
}

private fun playbackErrorMessage(failure: PlaybackFailure?): String = when (failure) {
    PlaybackFailure.SOUND_NOT_FOUND -> "The selected sound could not be found."
    PlaybackFailure.DECODER_ERROR -> "The selected sound could not be played."
    PlaybackFailure.LIFECYCLE_UNAVAILABLE -> "Audio playback is currently unavailable."
    PlaybackFailure.UNKNOWN, null -> "Playback failed. Please try again."
}
