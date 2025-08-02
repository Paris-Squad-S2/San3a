package com.paris_2.san3a.presentation.screen.register.registerScreen

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text.filter { it.isDigit() }

        val trimmed = if (input.length >= 10) input.substring(0..9) else input

        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 2 || i == 5) append('-')
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 6 -> offset + 1
                    offset <= 10 -> offset + 2
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset - 1
                    offset <= 12 -> offset - 2
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}
