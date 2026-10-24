package com.saishaddai

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.saishaddai.feature.soundboard.SoundItem

class MainActivity : ComponentActivity() {
    private var activePlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                sounds = listOf(
                    SoundItem("ahh", "Aah"),
                    SoundItem("explosion", "Explosion"),
                    SoundItem("nyan", "Nyan"),
                    SoundItem("oneechan", "Oneechan"),
                    SoundItem("tuturu", "Tuturu"),
                ),
                onPlaySound = { sound -> playSound(sound.id) },
            )
        }
    }

    private fun playSound(soundId: String) {
        val resourceId = when (soundId) {
            "ahh" -> R.raw.ahh
            "explosion" -> R.raw.explosion
            "nyan" -> R.raw.nyan
            "oneechan" -> R.raw.oneechan
            "tuturu" -> R.raw.tuturu
            else -> return
        }

        if (activePlayer?.isPlaying == true) {
            return
        }

        activePlayer?.release()
        activePlayer = MediaPlayer.create(this, resourceId)
        activePlayer?.setOnCompletionListener { player ->
            player.release()
            if (activePlayer === player) {
                activePlayer = null
            }
        }
        activePlayer?.start()
    }

    override fun onDestroy() {
        activePlayer?.release()
        activePlayer = null
        super.onDestroy()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        sounds = listOf(
            SoundItem("ahh", "Aah"),
            SoundItem("explosion", "Explosion"),
            SoundItem("nyan", "Nyan"),
            SoundItem("oneechan", "Oneechan"),
            SoundItem("tuturu", "Tuturu"),
        ),
        onPlaySound = {},
    )
}