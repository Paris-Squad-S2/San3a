package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AdCard
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme

@Composable
fun BecomeCraftsmanCard(
    onClick: () -> Unit,
) {
    AdCard(
        title = stringResource(R.string.craftsman_title),
        caption = stringResource(R.string.craftsman_desc),
        buttonTitle = stringResource(R.string.craftsman_button),
        onClick = onClick
    )
}

@Preview(
    name = "Become Craftsman Card - LTR",
    showBackground = true,
    locale = "en"
)
@Preview(
    name = "Become Craftsman Card - RTL (Arabic)",
    showBackground = true,
    locale = "ar"
)
@Composable
fun BecomeCraftsmanCardPreview() {
    San3aTheme {
        BecomeCraftsmanCard(
            onClick = {}
        )
    }
}
