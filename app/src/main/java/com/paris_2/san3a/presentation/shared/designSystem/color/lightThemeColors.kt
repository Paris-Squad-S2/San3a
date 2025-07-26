package com.paris_2.san3a.presentation.shared.designSystem.color

import androidx.compose.ui.graphics.Color

// TODO: Update the colors to match the design system of San3a app
val lightThemeColors = San3aColors(
    background = Background(
        screen = Color(0xFFFFFFFF),
        card = Color(0xFFF5F5F5),
        bottomSheet = Color(0xFFE0E0E0),
        bottomSheetCard = Color(0xFFF5F5F5)
    ),
    shade = Shade(
        primary = Color(0xFF212121),
        secondary = Color(0xFF757575),
        tertiary = Color(0xFFBDBDBD),
        quaternary = Color(0xFFE0E0E0),
        quinary = Color(0xFFF5F5F5)
    ),
    brand = Brand(
        primary = Color(0xFF1976D2),
        secondary = Color(0xFF64B5F6),
        tertiary = Color(0xFFBBDEFB)
    ),
    button = Button(
        primary = Color(0xFF1976D2),
        secondary = Color(0xFF64B5F6),
        disabled = Color(0xFFBDBDBD),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF212121),
        onDisabled = Color(0xFF757575),
        onTertiary = Color(0xFF212121)
    ),
    stroke = Stroke(
        primary = Color(0xFFBDBDBD)
    ),
    overlay = Overlay(
        primary = Color(0x1A000000)
    ),
    additional = Additional(
        primary = AdditionalColors(
            error = Color(0xFFD32F2F),
            success = Color(0xFF388E3C),
            warning = Color(0xFFFBC02D),
            purple = Color(0xFF8E24AA),
            red = Color(0xFFD32F2F),
            blue = Color(0xFF1976D2),
            turquoise = Color(0xFF1DE9B6),
            yellow = Color(0xFFFFEB3B),
            green = Color(0xFF388E3C)
        ),
        secondary = AdditionalColors(
            error = Color(0xFFFFCDD2),
            success = Color(0xFFC8E6C9),
            warning = Color(0xFFFFF9C4),
            purple = Color(0xFFE1BEE7),
            red = Color(0xFFFFCDD2),
            blue = Color(0xFFBBDEFB),
            turquoise = Color(0xFFB2DFDB),
            yellow = Color(0xFFFFF9C4),
            green = Color(0xFFC8E6C9)
        )
    )
)