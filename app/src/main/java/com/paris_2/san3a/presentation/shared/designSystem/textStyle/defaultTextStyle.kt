package com.paris_2.san3a.presentation.shared.designSystem.textStyle

import androidx.compose.ui.text.TextStyle

// TODO: Refactor this file to use the text style of the San3a app
val defaultTextStyle = San3aTextStyle(
    display = SizedDisplayTextStyle(
        xl = TextStyle.Default
    ),
    title = SizedTitleTextStyle(
        xl = TextStyle.Default,
        lg = TextStyle.Default,
        md = TextStyle.Default,
        sm = TextStyle.Default
    ),
    body = SizedBodyTextStyle(
        lg = WeightedBodyTextStyle(
            regular = TextStyle.Default,
            medium = TextStyle.Default,
            semibold = TextStyle.Default
        ),
        md = WeightedBodyTextStyle(
            regular = TextStyle.Default,
            medium = TextStyle.Default,
            semibold = TextStyle.Default
        ),
        sm = WeightedBodyTextStyle(
            regular = TextStyle.Default,
            medium = TextStyle.Default,
            semibold = TextStyle.Default
        )
    ),
    label = SizedLabelTextStyle(
        md = WeightedLabelTextStyle(
            regular = TextStyle.Default,
            medium = TextStyle.Default,
            semibold = TextStyle.Default
        )
    )
)