package com.saishaddai

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SonicPulsePrimary = Color(0xFFB6A0FF)
private val SonicPulsePrimaryDim = Color(0xFF7E51FF)
private val SonicPulseSecondary = Color(0xFF00E3FD)
private val SonicPulseTertiary = Color(0xFFFF6B99)
private val SonicPulseBackground = Color(0xFF0E0E0E)
private val SonicPulseSurfaceLowest = Color(0xFF000000)
private val SonicPulseSurfaceLow = Color(0xFF131313)
private val SonicPulseSurface = Color(0xFF1A1A1A)
private val SonicPulseSurfaceHigh = Color(0xFF20201F)
private val SonicPulseSurfaceHighest = Color(0xFF262626)
private val SonicPulseOnSurface = Color(0xFFFFFFFF)
private val SonicPulseOnSurfaceVariant = Color(0xFFADAAAA)
private val SonicPulseOutlineVariant = Color(0xFF484847)
private val SonicPulseError = Color(0xFFFF6E84)

@Composable
fun Shake2PlayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = SonicPulsePrimary,
            onPrimary = Color(0xFF340090),
            primaryContainer = Color(0xFFA98FFF),
            onPrimaryContainer = Color(0xFF280072),
            secondary = SonicPulseSecondary,
            onSecondary = Color(0xFF004D57),
            secondaryContainer = Color(0xFF006875),
            onSecondaryContainer = Color(0xFFE8FBFF),
            tertiary = SonicPulseTertiary,
            onTertiary = Color(0xFF47001E),
            tertiaryContainer = Color(0xFFFF097D),
            onTertiaryContainer = Color(0xFF000000),
            error = SonicPulseError,
            onError = Color(0xFF490013),
            errorContainer = Color(0xFFA70138),
            onErrorContainer = Color(0xFFFFB2B9),
            background = SonicPulseBackground,
            onBackground = SonicPulseOnSurface,
            surface = SonicPulseBackground,
            onSurface = SonicPulseOnSurface,
            surfaceVariant = SonicPulseSurfaceHighest,
            onSurfaceVariant = SonicPulseOnSurfaceVariant,
            outline = Color(0xFF767575),
            outlineVariant = SonicPulseOutlineVariant,
            inverseSurface = Color(0xFFFCF9F8),
            inverseOnSurface = Color(0xFF565555),
            inversePrimary = Color(0xFF6834EB),
            surfaceDim = SonicPulseBackground,
            surfaceBright = Color(0xFF2C2C2C),
            surfaceContainerLowest = SonicPulseSurfaceLowest,
            surfaceContainerLow = SonicPulseSurfaceLow,
            surfaceContainer = SonicPulseSurface,
            surfaceContainerHigh = SonicPulseSurfaceHigh,
            surfaceContainerHighest = SonicPulseSurfaceHighest,
        ),
        typography = Typography(
            displayLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 56.sp,
                letterSpacing = (-2).sp,
            ),
            headlineLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
            ),
            headlineMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
            titleMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            ),
            labelLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
            ),
            labelMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 0.12.sp,
            ),
        ),
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(20.dp),
            extraLarge = RoundedCornerShape(28.dp),
        ),
        content = content,
    )
}

internal val SonicPulsePrimaryGradient = listOf(SonicPulsePrimary, SonicPulsePrimaryDim)
