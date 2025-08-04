package com.paris_2.san3a.presentation.screen.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
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
        moreUiState = uiState.value,
        moreInteractionListener = moreViewModel
    )
}

@Composable
private fun MoreScreenContent(
    moreUiState: MoreUiState,
    moreInteractionListener: MoreInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        AppBar(
            modifier = Modifier
                .background(Theme.colors.background.card)
                .padding(horizontal = 16.dp),
            title = stringResource(R.string.more),
            actionIcon = { NotificationIcon() }
        )
        UserProfileSection(
            modifier = Modifier.padding(16.dp),
            name = moreUiState.name,
            rating = moreUiState.rating,
            review = moreUiState.review,
            isVerify = moreUiState.isVerify,
            phoneNumber = moreUiState.phoneNumber,
            isCraftsman = moreUiState.isCraftsman,
            painter = rememberAsyncImagePainter(moreUiState.imageUrl),
            onClickEdit = moreInteractionListener::onClickEdit,
        )

        // Change account type component

        AppSectionTitle(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 12.dp),
            title = stringResource(R.string.setting)
        )

        SettingItems(
            modifier = Modifier.padding(horizontal = 16.dp),
            isCraftsman = moreUiState.isCraftsman,
            isDarkMode = moreUiState.isDarkMode,
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
            text = stringResource(R.string.version, moreUiState.versionNumber),
            style = Theme.textStyle.body.small.regular,
            color = Theme.colors.shade.tertiary,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}


@PreviewMultiDevices
@Preview
@Composable
fun MoreScreenContentPreview() {
    BasePreview {
        MoreScreenContent(MoreUiState(), object : MoreInteractionListener {
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

        })
    }
}