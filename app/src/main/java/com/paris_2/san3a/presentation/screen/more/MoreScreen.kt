package com.paris_2.san3a.presentation.screen.more

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.more.components.BecomeCraftsman
import com.paris_2.san3a.presentation.screen.more.components.LogoutItem
import com.paris_2.san3a.presentation.screen.more.components.NotificationIcon
import com.paris_2.san3a.presentation.screen.more.components.SettingItems
import com.paris_2.san3a.presentation.screen.more.components.UserProfileSection
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
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
            .background(Color.Red)
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
            name = moreScreenState.moreUiState.name,
            rating = moreScreenState.moreUiState.rating,
            review = moreScreenState.moreUiState.review,
            isVerify = moreScreenState.moreUiState.isVerify,
            phoneNumber = moreScreenState.moreUiState.phoneNumber,
            isCraftsman = moreScreenState.moreUiState.isCraftsman,
            painter = rememberAsyncImagePainter(moreScreenState.moreUiState.imageUrl),
            onClickEdit = moreInteractionListener::onClickEdit,
        )

        AnimatedVisibility(
            visible = moreScreenState.moreUiState.isCraftsman,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            BecomeCraftsman(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickButton = moreInteractionListener::onClickBecomeACraftsman
            )
        }

        AppSectionTitle(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 12.dp),
            title = stringResource(R.string.setting)
        )

        SettingItems(
            modifier = Modifier.padding(horizontal = 16.dp),
            isCraftsman = moreScreenState.moreUiState.isCraftsman,
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

    }
}

@PreviewMultiDevices
@Preview
@Composable
fun MoreScreenContentPreview() {
    BasePreview {
        MoreScreenContent(MoreScreenState(), object : MoreInteractionListener {
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

        })
    }
}