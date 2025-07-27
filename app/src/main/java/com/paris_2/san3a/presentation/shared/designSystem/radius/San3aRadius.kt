package com.paris_2.san3a.presentation.shared.designSystem.radius

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class San3aRadius(
    val none: Dp,
    val extraExtraSmall: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val extraExtraLarge: Dp,
    val tripleXLarge: Dp,
    val quadXLarge: Dp,
    val quintXLarge: Dp,
    val full: Dp,
)

val LocalSan3aRadius = staticCompositionLocalOf { defaultSan3aRadius }