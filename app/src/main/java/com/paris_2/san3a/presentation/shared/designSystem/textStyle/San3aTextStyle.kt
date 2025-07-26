package com.paris_2.san3a.presentation.shared.designSystem.textStyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class San3aTextStyle(
    val display: SizedDisplayTextStyle,
    val title: SizedTitleTextStyle,
    val body: SizedBodyTextStyle,
    val label: SizedLabelTextStyle,
)

data class SizedDisplayTextStyle(
    val xLarge: TextStyle,
)

data class SizedTitleTextStyle(
    val xLarge: TextStyle,
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
)

data class SizedBodyTextStyle(
    val large: WeightedBodyTextStyle,
    val medium: WeightedBodyTextStyle,
    val small: WeightedBodyTextStyle,
)

data class WeightedBodyTextStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semibold: TextStyle
)

data class SizedLabelTextStyle(
    val medium: WeightedLabelTextStyle
)

data class WeightedLabelTextStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semibold: TextStyle
)

val LocalSan3aTextStyle = staticCompositionLocalOf { defaultTextStyle }