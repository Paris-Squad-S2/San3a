package com.paris_2.san3a.presentation.screen.myService

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.account.components.ServicesContent
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.SnackBar
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MyServiceScreen(myServiceViewModel: MyServiceViewModel = koinViewModel()) {
    val uiState = myServiceViewModel.screenState.collectAsStateWithLifecycle()

    MyServiceScreenContent(
        myServiceScreenState = uiState.value,
        myServiceInteractionListener = myServiceViewModel,
    )
}

@Composable
fun MyServiceScreenContent(
    myServiceScreenState: MyServiceScreenState,
    myServiceInteractionListener: MyServiceInteractionListener
) {

    val scroll = rememberScrollState()


    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .verticalScroll(scroll)
            .statusBarsPadding()
    ) {
        when {
            myServiceScreenState.isNoInternet -> {
                LostConnectionScreen(
                    onRetry = myServiceInteractionListener::onClickRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 60.dp)
                )
            }

            myServiceScreenState.isLoading -> {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AppBar(
                        title = stringResource(R.string.my_services),
                        modifier = Modifier.fillMaxWidth(),
                        onBackClick = myServiceInteractionListener::onBackClick
                    )

                    Text(
                        text = stringResource(R.string.what_do_you_usually_need_help_with),
                        style = Theme.textStyle.display.xLarge,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = stringResource(R.string.this_helps_us_personalize_your_experience_you_can_change_it_anytime),
                        style = Theme.textStyle.body.large.regular,
                        color = Theme.colors.shade.secondary,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .padding(horizontal = 16.dp)
                    )

                    ServicesContent(
                        services = myServiceScreenState.myServiceUiState,
                        onChipClick = myServiceInteractionListener::onClickService,
                        modifier = Modifier
                            .height(434.dp)
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 32.dp)
                    )

                    AppButton(
                        text = stringResource(R.string.save),
                        onClick = myServiceInteractionListener::onClickSave,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        state = AppButtonState.Enable,
                        type = AppButtonType.Primary,
                    )

                }
            }
        }

        AnimatedVisibility(myServiceScreenState.showSnackBarError) {
            myServiceScreenState.errorMessage?.let {
                SnackBar(
                    text = myServiceScreenState.errorMessage,
                )
            }
        }

        AnimatedVisibility(myServiceScreenState.showSnackBarSuccess) {
            myServiceScreenState.successMessageSnackBar?.let {
                SnackBar(
                    text = myServiceScreenState.successMessageSnackBar,
                )
            }
        }
    }

}

@Preview
@Composable
private fun MyServiceScreenContentPreview() {
    MyServiceScreenContent(
        myServiceScreenState = MyServiceScreenState(),
        myServiceInteractionListener = object : MyServiceInteractionListener {
            override fun onBackClick() {}
            override fun onClickSave() {
            }

            override fun onClickRetry() {

            }

            override fun onClickService(service: String) {

            }
        },

        )

}