package com.paris_2.san3a.presentation.screen.register.registerScreen

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text.filter { it.isDigit() }

        val trimmed = if (input.length >= 11) input.substring(0..10) else input

        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 2 || i == 6) append('-')
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset + 1
                    offset <= 11 -> offset + 2
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 8 -> offset - 1
                    offset <= 13 -> offset - 2
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}
