package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AppBackButton(
    onClickBackButton: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    painter: Painter = painterResource(id = R.drawable.ic_arrow_left_outline),
    iconContentDescription: String? = null
) {
    IconButton(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(Theme.radius.full)),
        onClick = { onClickBackButton() },
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(containerColor = Theme.colors.background.card),
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = iconContentDescription,
            tint = Theme.colors.shade.primary
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun AppBackButtonPreview() {
    San3aTheme {
        AppBackButton(
            onClickBackButton = {}
        )
    }
}