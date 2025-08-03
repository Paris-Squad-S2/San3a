package com.paris_2.san3a.presentation.screen.more.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

private data class SettingAccountItem(
    @StringRes val label: Int,
    @StringRes val icon: Int
)

private fun settingAccountItems(): List<SettingAccountItem> {
    val items = listOf<Pair<Int, Int>>(
        R.string.switch_to_customer to R.drawable.ic_home_outline,
        R.string.dark_mode to R.drawable.ic_moon_outline,
        R.string.language to R.drawable.ic_language_circle_outline,
        R.string.my_location to R.drawable.ic_location_outline,
        R.string.my_services to R.drawable.ic_sledgehammer_outline,
        R.string.verification to R.drawable.ic_verified_check_outline
    )

    return items.mapIndexed { index, item ->
        SettingAccountItem(
            label = item.first,
            icon = item.second,
        )
    }
}

@Composable
fun SettingAccountItems(
    modifier: Modifier = Modifier,
    isCheckSwitch: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    onClickItem: () -> Unit,

    ) {
    val items = settingAccountItems()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.extraLarge))
            .clip(RoundedCornerShape(Theme.radius.extraLarge))
            .padding(horizontal = 16.dp)
    ) {
        items.forEachIndexed { index, item ->
            SettingItem(
                icon = item.icon,
                label = item.label,
                hasSwitchIcon = item.label == R.string.dark_mode,
                isCheckSwitch = isCheckSwitch,
                onCheckedChange = { onCheckedChange(it) },
                onClickItem = { onClickItem() }
            )
            AnimatedVisibility(index != items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Theme.colors.shade.quaternary
                )
            }


        }

    }

}

@PreviewMultiDevices
@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun SettingAccountItemsPreview() {
    BasePreview(modifier = Modifier
        .fillMaxSize()
        .background(Theme.colors.background.screen)) {
        SettingAccountItems {}
    }
}
