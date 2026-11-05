package com.saishaddai.feature.soundboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun SoundLibraryScreen(
    state: SoundboardState,
    soundPlayerAvailable: Boolean,
    onSelectSound: (String) -> Unit,
    onPreviewSound: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchQuery by remember { mutableStateOf("") }
    val visibleSounds = filterSoundItems(state.sounds, searchQuery)
    val selectedSound = state.selectedSound

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "SHAKE 2 PLAY",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "SOUND LIBRARY",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = "Your sounds",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search sounds") },
            singleLine = true,
        )

        Text(
            text = if (searchQuery.isBlank()) {
                "${state.sounds.size} sounds available"
            } else {
                "${visibleSounds.size} of ${state.sounds.size} sounds"
            },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        when {
            state.isEmpty -> SoundLibraryMessage(
                title = "No sounds yet",
                description = "Your sound library will appear here.",
            )
            visibleSounds.isEmpty() -> SoundLibraryMessage(
                title = "No matching sounds",
                description = "Try a different search.",
            )
            else -> {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    visibleSounds.forEach { sound ->
                        val isSelected = sound.id == state.selectedSoundId
                        Button(
                            onClick = { onSelectSound(sound.id) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { selected = isSelected },
                            shape = MaterialTheme.shapes.large,
                            enabled = state.playbackState != PlaybackState.PLAYING,
                            colors = if (isSelected) {
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                        ) {
                            Text(text = sound.title)
                        }
                    }
                }
            }
        }

        if (selectedSound != null) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "SELECTED",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = selectedSound.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    OutlinedButton(
                        onClick = onPreviewSound,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        enabled = state.canPreview && soundPlayerAvailable,
                    ) {
                        Text(
                            text = if (state.playbackState == PlaybackState.PLAYING) {
                                "Playing..."
                            } else {
                                "Preview sound"
                            },
                        )
                    }
                    if (!soundPlayerAvailable) {
                        Text(
                            text = "Audio playback is not available on this platform.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

internal fun filterSoundItems(
    sounds: List<SoundItem>,
    query: String,
): List<SoundItem> {
    val normalizedQuery = query.trim()
    return if (normalizedQuery.isEmpty()) {
        sounds
    } else {
        sounds.filter { sound -> sound.title.contains(normalizedQuery, ignoreCase = true) }
    }
}

@Composable
private fun SoundLibraryMessage(
    title: String,
    description: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
