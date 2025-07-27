package com.paris_2.san3a.presentation.shared.designSystem.color

import androidx.compose.ui.graphics.Color

val darkThemeColors = San3aColors(
    background = Background(
        screen = Color(0xFF121321),
        card = Color(0xFF1B1C2A),
        bottomSheet = Color(0xFF1B1C2A),
        bottomSheetCard = Color(0xFF242533)
    ),
    shade = Shade(
        primary = Color(0xFFE1E1E3),
        secondary = Color(0xFFA4A4AA),
        tertiary = Color(0xFF72727B),
        quaternary = Color(0xFF2D2E3B),
        quinary = Color(0xFF242533)
    ),
    brand = Brand(
        primary = Color(0xFF5C9EFF),
        secondary = Color(0xFF344D7B),
        tertiary = Color(0xFF20263B)
    ),
    button = Button(
        primary = Color(0xFF5C9EFF),
        secondary = Color(0xFF20263B),
        disabled = Color(0xFF2D2E3B),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFFE1E1E3),
        onDisabled = Color(0xFF72727B),
        onTertiary = Color(0xFF5C9EFF)
    ),
    stroke = Stroke(
        primary = Color(0xFF24263B)
    ),
    overlay = Overlay(
        primary = Color(0x99121321)
    ),
    additional = Additional(
        primary = AdditionalColors(
            error = Color(0xFFFF6B6B),
            success = Color(0xFF00E676),
            warning = Color(0xFFFFD600),
            purple = Color(0xFF9A83CE),
            red = Color(0xFFF56C6C),
            blue = Color(0xFF4C8FD3),
            turquoise = Color(0xFF4BA8A7),
            yellow = Color(0xFFE3B339),
            green = Color(0xFF6DBF7E)
        ),
        secondary = AdditionalColors(
            error = Color(0xFF2C202D),
            success = Color(0xFF232A31),
            warning = Color(0xFF2D2927),
            purple = Color(0xFF252437),
            red = Color(0xFF2C222F),
            blue = Color(0xFF1F2538),
            turquoise = Color(0xFF1F2734),
            yellow = Color(0xFF2B282B),
            green = Color(0xFF222931)
        )
    )
)