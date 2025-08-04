package com.paris_2.san3a.presentation.screen.more

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.more.components.BecomeCraftsmanCard
import com.paris_2.san3a.presentation.screen.more.components.ChangeLanguageBottomSheet
import com.paris_2.san3a.presentation.screen.more.components.EditProfileBottomSheet
import com.paris_2.san3a.presentation.screen.more.components.LogoutItem
import com.paris_2.san3a.presentation.screen.more.components.NotificationIcon
import com.paris_2.san3a.presentation.screen.more.components.SettingItems
import com.paris_2.san3a.presentation.screen.more.components.UserProfileSection
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppSectionTitle
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoreScreen(
    moreViewModel: MoreViewModel = koinViewModel()
) {
    val uiState = moreViewModel.screenState.collectAsStateWithLifecycle()
    MoreScreenContent(
        moreScreenState = uiState.value,
        moreInteractionListener = moreViewModel
    )
}

@Composable
private fun MoreScreenContent(
    moreScreenState: MoreScreenState,
    moreInteractionListener: MoreInteractionListener,
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .verticalScroll(scroll)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        AppBar(
            modifier = Modifier
                .background(Theme.colors.background.card)
                .padding(horizontal = 16.dp),
            title = stringResource(R.string.more),
            actionIcon = {
                NotificationIcon(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = moreInteractionListener::onClickNotification
                    )
                )
            }
        )
        UserProfileSection(
            modifier = Modifier.padding(16.dp),
            name = moreScreenState.moreUiState.userUiState.name,
            rating = moreScreenState.moreUiState.userUiState.rating,
            review = moreScreenState.moreUiState.userUiState.review,
            isVerify = moreScreenState.moreUiState.userUiState.isVerify,
            phoneNumber = moreScreenState.moreUiState.userUiState.phoneNumber,
            isCraftsman = moreScreenState.moreUiState.userUiState.isCraftsman,
            painter = rememberAsyncImagePainter(moreScreenState.moreUiState.userUiState.imageUrl),
            onClickEdit = moreInteractionListener::onClickEditProfileBottomSheet,
        )

        AnimatedVisibility(
            visible = moreScreenState.moreUiState.userUiState.isCraftsman,
            modifier = Modifier.padding(top = 8.dp)
        ) {

            BecomeCraftsmanCard(moreInteractionListener::onClickBecomeACraftsman)
        }

        AppSectionTitle(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 12.dp),
            title = stringResource(R.string.setting)
        )

        SettingItems(
            modifier = Modifier.padding(horizontal = 16.dp),
            isCraftsman = moreScreenState.moreUiState.userUiState.isCraftsman,
            isDarkMode = moreScreenState.moreUiState.isDarkMode,
            moreInteractionListener = moreInteractionListener
        )
        LogoutItem(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
                .background(
                    Theme.colors.background.card,
                    RoundedCornerShape(Theme.radius.extraLarge)
                )
                .clip(RoundedCornerShape(Theme.radius.extraLarge)),
            onClickItem = moreInteractionListener::onClickLogout
        )

        Text(
            text = stringResource(R.string.version, moreScreenState.moreUiState.versionNumber),
            style = Theme.textStyle.body.small.regular,
            color = Theme.colors.shade.tertiary,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(moreScreenState.showEditProfileBottomSheet) {
            EditProfileBottomSheet(
                name = moreScreenState.moreUiState.userUiState.name,
                profileUri = moreScreenState.moreUiState.userUiState.imageUrl,
                onNameChange = moreInteractionListener::onNameValueChange,
                onDismissRequest = moreInteractionListener::onCloseEditProfileBottomSheet,
                isVisible = moreScreenState.showEditProfileBottomSheet,
                onPickImageClick = moreInteractionListener::onPickImageClick
            )
        }

        AnimatedVisibility(moreScreenState.showLanguageBottomSheet) {
            ChangeLanguageBottomSheet(
                isVisible = moreScreenState.showLanguageBottomSheet,
                onDismissRequest = moreInteractionListener::onCloseSelectedLanguageBottomSheet,
                selectedLanguage = moreScreenState.moreUiState.selectedLanguage,
                onLanguageSelected = moreInteractionListener::onLanguageSelected,
            )
        }

    }
}

@PreviewMultiDevices
@Preview
@Composable
fun MoreScreenContentPreview() {
    BasePreview {
        MoreScreenContent(MoreScreenState(), object : MoreInteractionListener {
            override fun onClickEditProfileBottomSheet() {

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

            override fun onNameValueChange(name: String) {

            }

            override fun onCloseEditProfileBottomSheet() {
            }

            override fun onCloseSelectedLanguageBottomSheet() {

            }

            override fun onLanguageSelected(language: String) {
            }

            override fun onPickImageClick() {

            }

        })
    }
}