package com.paris_2.san3a.presentation.shared.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ServiceTypeCard(title: String, serviceType: String, serviceId: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CategoryIconBox(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            tint = getResourceTint(serviceId),
            iconColor = getResourceColors(serviceId),
            painter = painterResource(getResource(serviceId)),
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