package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun LocationCard(modifier: Modifier = Modifier, title: String, onClick: (String) -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(
                enabled = true, onClick = {
                    onClick(title)
                }, interactionSource = remember { MutableInteractionSource() }, indication = null
            ),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.bottomSheetCard)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = Theme.textStyle.body.medium.medium,
                modifier = Modifier.weight(1F),
                color = Theme.colors.shade.primary
            )
            Icon(
                painter = painterResource(
                    R.drawable.ic_alt_arrow_right_outline
                ), contentDescription = null,
                tint = Theme.colors.shade.tertiary
            )
        }
    }
}