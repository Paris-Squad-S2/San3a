package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.more.MoreInteractionListener
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun SettingItems(
    isCraftsman: Boolean,
    isDarkMode: Boolean,
    moreInteractionListener: MoreInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.extraLarge))
            .clip(RoundedCornerShape(Theme.radius.extraLarge))

    ) {
        AnimatedContent(isCraftsman) {
            if (it) {
                SettingItem(
                    icon = R.drawable.ic_home_outline,
                    label = R.string.switch_to_customer,
                    onClickItem = moreInteractionListener::onClickSwitchAccountToCustomer
                )

            } else {
                SettingItem(
                    icon = R.drawable.ic_sledgehammer_outline,
                    label = R.string.switch_to_craftsman,
                    onClickItem = moreInteractionListener::onClickSwitchAccountToCraftsman
                )
            }
        }

        SettingHorizontalDivider()
        SettingItem(
            icon = R.drawable.ic_moon_outline,
            label = R.string.dark_mode,
            hasSwitchIcon = true,
            isCheckSwitch = isDarkMode,
            onCheckedChange = moreInteractionListener::onChangeToDarkMode
        )
        SettingHorizontalDivider()
        SettingItem(
            icon = R.drawable.ic_language_circle_outline,
            label = R.string.language,
            onClickItem = moreInteractionListener::onClickLanguage
        )
        SettingHorizontalDivider()
        SettingItem(
            icon = R.drawable.ic_location_outline,
            label = R.string.my_location,
            onClickItem = moreInteractionListener::onClickLocation
        )
        AnimatedVisibility(isCraftsman) {
            Column {
                SettingItem(
                    icon = R.drawable.ic_sledgehammer_outline,
                    label = R.string.my_services,
                    onClickItem = moreInteractionListener::onClickSwitchAccountToCraftsman
                )
                SettingItem(
                    icon = R.drawable.ic_verified_check_outline,
                    label = R.string.verification,
                    onClickItem = moreInteractionListener::onClickSwitchAccountToCraftsman
                )
            }

        }


    }

}

@PreviewMultiDevices
@Preview
@Composable
private fun SettingItemsPreview() {
    BasePreview {
        SettingItems(
            isCraftsman = true,
            isDarkMode = false,
            moreInteractionListener = object : MoreInteractionListener {
                override fun onClickEdit() {

                }

                override fun onClickSwitchAccountToCraftsman() {
                }

                override fun onClickSwitchAccountToCustomer() {
                }

                override fun onClickLanguage() {
                }

                override fun onClickLocation() {
                }

                override fun onClickLogout() {
                }

                override fun onChangeToDarkMode(isDarkMode: Boolean) {
                }

                override fun onClickNotification() {

                }

                override fun onClickBecomeACraftsman() {
                }

            }
        )
    }
}