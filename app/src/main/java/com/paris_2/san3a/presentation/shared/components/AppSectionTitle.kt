package com.paris_2.san3a.presentation.shared.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AppSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    hasShowMore: Boolean = false,
    onClickShowMore: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (hasShowMore) {
            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.brand.primary,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClickShowMore
                    )
                    .padding(end = if(icon != null) 12.dp else 0.dp)
            )
        }
        if (icon != null) {
            icon()
        }
    }

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun AppSectionTitlePreview() {
    San3aTheme {
        AppSectionTitle(
            title = "Section Title",
            hasShowMore = false,
            onClickShowMore = {}
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun AppSectionTitleWithShowMorePreview() {
    San3aTheme {
        AppSectionTitle(
            title = "Section Title",
            hasShowMore = true,
            onClickShowMore = {}
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun AppSectionTitleWithShowMoreAndIconPreview() {
    San3aTheme {
        AppSectionTitle(
            title = "Section Title",
            hasShowMore = true,
            onClickShowMore = {},
            icon = { Icon(
                painter = painterResource(R.drawable.icon_cancel),
                contentDescription = "icon cancel",
                tint = Theme.colors.shade.secondary
            ) }
        )
    }
}