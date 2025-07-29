package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
    painter: Painter = painterResource(id = R.drawable.ic_arrow_left_outline),
    iconContentDescription: String? = null
) {
    Button(
        modifier = modifier
            .size(48.dp)
            .background(
                color = Theme.colors.background.card,
                shape = RoundedCornerShape(Theme.radius.full)
            )
            .clip(RoundedCornerShape(Theme.radius.full)),
        onClick = { onClickBackButton() },
        colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.background.card),
        contentPadding = PaddingValues(12.dp)
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