package com.paris_2.san3a.presentation.shared.designSystem.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class San3aColors(
    val background: Background,
    val shade: Shade,
    val brand: Brand,
    val button: Button,
    val stroke: Stroke,
    val overlay: Overlay,
    val additional: Additional
)

data class Background(
    val screen: Color,
    val card: Color,
    val bottomSheet: Color,
    val bottomSheetCard: Color
)

data class Shade(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val quaternary: Color,
    val quinary: Color
)

data class Brand(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

data class Button(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onDisabled: Color,
    val onTertiary: Color,
)

data class Stroke(
    val primary: Color
)

data class Overlay(
    val primary: Color
)

data class Additional(
    val primary: AdditionalColors,
    val secondary: AdditionalColors,
)

data class AdditionalColors(
    val error: Color,
    val success: Color,
    val warning: Color,
    val purple: Color,
    val red: Color,
    val blue: Color,
    val turquoise: Color,
    val yellow: Color,
    val green: Color,
)

val LocalSan3aColors = staticCompositionLocalOf { lightThemeColors }