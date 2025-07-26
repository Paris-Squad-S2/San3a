package com.paris_2.san3a.presentation.shared.designSystem.radius

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class San3aRadius(
    val none: Dp,
    val `2xs`: Dp,
    val xs: Dp,
    val s: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,
    val `2xl`: Dp,
    val `3xl`: Dp,
    val `4xl`: Dp,
    val `5xl`: Dp,
    val full: Dp
)

val LocalSan3aRadius = staticCompositionLocalOf { defaultSan3aRadius }