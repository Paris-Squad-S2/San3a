package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

data class SelectionItemData(
    val title: String,
    val showIcon: Boolean = false
)
@Composable
fun SelectionItem(
    item:SelectionItemData,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                color = Theme.colors.background.bottomSheetCard,
            )
            .clip(shape)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = item.title,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.regular
        )

        if (item.showIcon) {
            Icon(
                painter = painterResource(R.drawable.ic_alt_arrow_right_outline),
                contentDescription = null,
                tint = Theme.colors.shade.tertiary
            )
        }
    }
}
