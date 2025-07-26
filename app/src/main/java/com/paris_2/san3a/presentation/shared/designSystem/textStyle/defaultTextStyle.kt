package com.paris_2.san3a.presentation.shared.designSystem.textStyle

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val defaultTextStyle = San3aTextStyle(
    display = SizedDisplayTextStyle(
        xLarge = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
    ),
    title = SizedTitleTextStyle(
        xLarge = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        ),
        large = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        ),
        medium = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        ),
        small = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    ),
    body = SizedBodyTextStyle(
        large = WeightedBodyTextStyle(
            regular = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            medium = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            semibold = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        ),
        medium = WeightedBodyTextStyle(
            regular = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            medium = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            semibold = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        ),
        small = WeightedBodyTextStyle(
            regular = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            semibold = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        )
    ),
    label = SizedLabelTextStyle(
        medium = WeightedLabelTextStyle(
            regular = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            medium = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            semibold = TextStyle(
                fontFamily = manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        )
    )
)