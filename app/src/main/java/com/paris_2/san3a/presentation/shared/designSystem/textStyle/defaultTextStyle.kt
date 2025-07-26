package com.paris_2.san3a.presentation.shared.designSystem.textStyle

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val defaultTextStyle = San3aTextStyle(
    display = SizedDisplayTextStyle(
        xl = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
    ),
    title = SizedTitleTextStyle(
        xl = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        ),
        lg = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        ),
        md = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        ),
        sm = TextStyle(
            fontFamily = manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    ),
    body = SizedBodyTextStyle(
        lg = WeightedBodyTextStyle(
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
        md = WeightedBodyTextStyle(
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
        sm = WeightedBodyTextStyle(
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
        md = WeightedLabelTextStyle(
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