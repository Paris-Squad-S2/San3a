package com.paris_2.san3a.presentation.shared.designSystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.paris_2.san3a.presentation.shared.designSystem.color.LocalSan3aColors
import com.paris_2.san3a.presentation.shared.designSystem.color.darkThemeColors
import com.paris_2.san3a.presentation.shared.designSystem.color.lightThemeColors
import com.paris_2.san3a.presentation.shared.designSystem.radius.LocalSan3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.radius.defaultSan3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.textStyle.LocalSan3aTextStyle
import com.paris_2.san3a.presentation.shared.designSystem.textStyle.defaultTextStyle

@Composable
fun San3aTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) darkThemeColors else lightThemeColors

    CompositionLocalProvider(
        LocalSan3aColors provides colors,
        LocalSan3aTextStyle provides defaultTextStyle,
        LocalSan3aRadius provides defaultSan3aRadius
    ){
        content()
    }
}

