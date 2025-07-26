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
    val xl: TextStyle,
)

data class SizedTitleTextStyle(
    val xl: TextStyle,
    val lg: TextStyle,
    val md: TextStyle,
    val sm: TextStyle,
)

data class SizedBodyTextStyle(
    val lg: WeightedBodyTextStyle,
    val md: WeightedBodyTextStyle,
    val sm: WeightedBodyTextStyle,
)

data class WeightedBodyTextStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semibold: TextStyle
)

data class SizedLabelTextStyle(
    val md: WeightedLabelTextStyle
)

data class WeightedLabelTextStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semibold: TextStyle
)

val LocalSan3aTextStyle = staticCompositionLocalOf { defaultTextStyle }