package com.paris_2.san3a.presentation.shared.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText{
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val resId: Int): UiText()
}

@Composable
fun UiText?.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResource -> stringResource(id = resId)
        else -> ""
    }
}