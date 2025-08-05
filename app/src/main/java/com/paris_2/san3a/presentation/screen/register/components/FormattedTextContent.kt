package com.paris_2.san3a.presentation.screen.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun FormattedTextContent(content: String) {
    val paragraphLines = content.split("\n")
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var i = 0
        while (i < paragraphLines.size) {
            val line = paragraphLines[i].trim()

            if (line.isEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                i++
                continue
            }

            val isTitle = "\\d+\\.".toRegex().find(line) != null

            Text(
                text = line,
                style = if (isTitle) {
                    Theme.textStyle.body.large.semibold
                } else {
                    Theme.textStyle.body.medium.medium
                },
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(bottom = if (i < paragraphLines.size - 1) 8.dp else 0.dp)
            )
            i++
        }
    }
}