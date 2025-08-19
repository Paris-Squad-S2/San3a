package com.paris_2.san3a.presentation.shared.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {

    data class DynamicString(val text: String) : UiText()

    class StringResource(@field:StringRes val resId: Int, vararg val args: Any = emptyArray()) :
        UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> handleStringResources(context, resId, *args)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> handleStringResources(LocalContext.current, resId, *args)
        }
    }

    private fun handleStringResources(
        context: Context,
        @StringRes resId: Int,
        vararg args: Any
    ): String {
        return runCatching { context.getString(resId, args) }.getOrDefault("none")
    }
}