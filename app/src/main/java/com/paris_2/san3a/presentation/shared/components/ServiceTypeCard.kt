package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ServiceTypeCard(title: String, serviceType: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Theme.colors.additional.secondary.blue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                /*todo check on type service and use the correct icon*/
                painter = painterResource(id = R.drawable.ic_conditioner_bold),
                contentDescription = null,
                tint = Theme.colors.brand.primary,
                modifier = Modifier.size(24.dp)
            )
        }

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