package com.saishaddai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.saishaddai.feature.soundboard.AndroidShakeDetector
import com.saishaddai.feature.soundboard.AndroidSoundPlayer
import com.saishaddai.feature.soundboard.SoundItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val soundPlayer = AndroidSoundPlayer(this)
        val shakeDetector = AndroidShakeDetector(this, this)

        setContent {
            App(
                sounds = listOf(
                    SoundItem("ahh", "Aah"),
                    SoundItem("explosion", "Explosion"),
                    SoundItem("nyan", "Nyan"),
                    SoundItem("oneechan", "Oneechan"),
                    SoundItem("tuturu", "Tuturu"),
                ),
                shakeDetector = shakeDetector,
                soundPlayer = soundPlayer,
            )
        }
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
    )
}