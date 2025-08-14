package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ServiceTypeCard(
    title: String,
    serviceType: String,
    serviceImageUrl: String,
    darkServiceImageUrl: String = "",
    isDarkTheme: Boolean? = null,
) {
    val shouldUseDark = (isDarkTheme ?: isSystemInDarkTheme()) && darkServiceImageUrl.isNotBlank()
    val imageUrlToShow = if (shouldUseDark) darkServiceImageUrl else serviceImageUrl

    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = imageUrlToShow,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                style = Theme.textStyle.body.medium.semibold,
                color = Theme.colors.shade.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = serviceType,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}