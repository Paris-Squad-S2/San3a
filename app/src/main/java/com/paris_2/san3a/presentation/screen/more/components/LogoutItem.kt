package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun LogoutItem(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit
) {
    SettingItem(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {onClickItem()}
        ),
        icon = R.drawable.ic_logout_outline,
        label = R.string.logout,
        onClickItem = {onClickItem()},
        labelColor = Theme.colors.additional.primary.error,
        iconLabelColor = Theme.colors.additional.primary.error
    )
}

@PreviewMultiDevices
@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun LogoutItemPreview(){
    BasePreview {
        LogoutItem{
        }
    }
}