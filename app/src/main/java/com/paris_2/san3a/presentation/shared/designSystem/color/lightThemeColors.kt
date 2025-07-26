package com.paris_2.san3a.presentation.shared.designSystem.color

import androidx.compose.ui.graphics.Color

val lightThemeColors = San3aColors(
    background = Background(
        screen = Color(0xFFF7F7F7),
        card = Color(0xFFFFFFFF),
        bottomSheet = Color(0xFFFFFFFF),
        bottomSheetCard = Color(0xFFF6F6F6)
    ),
    shade = Shade(
        primary = Color(0xFF313131),
        secondary = Color(0xFF717171),
        tertiary = Color(0xFFA5A5A5),
        quaternary = Color(0xFFECECEC),
        quinary = Color(0xFFF6F6F6)
    ),
    brand = Brand(
        primary = Color(0xFF5C9EFF),
        secondary = Color(0xFFC1DAFF),
        tertiary = Color(0xFFF2F7FF)
    ),
    button = Button(
        primary = Color(0xFF5C9EFF),
        secondary = Color(0xFFFFFFFF),
        disabled = Color(0xFFECECEC),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF313131),
        onDisabled = Color(0xFFA5A5A5),
        onTertiary = Color(0xFF5C9EFF)
    ),
    stroke = Stroke(
        primary = Color(0xFFEDEDED)
    ),
    overlay = Overlay(
        primary = Color(0xFF121212).copy(alpha = 0.6f)
    ),
    additional = Additional(
        primary = AdditionalColors(
            error = Color(0xFFEF4444),
            success = Color(0xFF22C55E),
            warning = Color(0xFFFACC15),
            purple = Color(0xFF9A83CE),
            red = Color(0xFFF56C6C),
            blue = Color(0xFF4C8FD3),
            turquoise = Color(0xFF4BA8A7),
            yellow = Color(0xFFE3B339),
            green = Color(0xFF6DBF7E)
        ),
        secondary = AdditionalColors(
            error = Color(0xFFEBEEEE),
            success = Color(0xFFEEF5EF),
            warning = Color(0xFFFFFAEB),
            purple = Color(0xFFF7F5FB),
            red = Color(0xFFFEF3F3),
            blue = Color(0xFFF1F6FB),
            turquoise = Color(0xFFF1F8F8),
            yellow = Color(0xFFFDF9EF),
            green = Color(0xFFF3FAF5)
        )
    )
)