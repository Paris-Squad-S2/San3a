package com.paris_2.san3a.presentation.shared.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.paris_2.san3a.presentation.shared.designSystem.color.San3aColors
import com.paris_2.san3a.presentation.shared.designSystem.color.LocalSan3aColors
import com.paris_2.san3a.presentation.shared.designSystem.radius.LocalSan3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.radius.San3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.textStyle.LocalSan3aTextStyle
import com.paris_2.san3a.presentation.shared.designSystem.textStyle.San3aTextStyle

object Theme {
    val colors: San3aColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSan3aColors.current

    val textStyle: San3aTextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalSan3aTextStyle.current

    val radius: San3aRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalSan3aRadius.current
}