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
import androidx.compose.ui.Alignment
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
import com.paris_2.san3a.presentation.shared.components.AppScaffold
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
    myServiceInteractionListener: MyServiceInteractionListener,
) {
    val scrollState = rememberScrollState()

    AppScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        topBar = {
            AppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.my_services),
                onBackClick = myServiceInteractionListener::onBackClick
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .verticalScroll(scrollState)
                    .padding(bottom = 96.dp)
            ) {
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
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp)
                        .height(434.dp)
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                AppButton(
                    text = stringResource(R.string.save),
                    onClick = myServiceInteractionListener::onClickSave,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    state = AppButtonState.Enable,
                    type = AppButtonType.Primary,
                )

                AnimatedVisibility(myServiceScreenState.showSnackBarError) {
                    myServiceScreenState.errorMessage?.let {
                        SnackBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                                .align(Alignment.TopCenter),
                            text = it,
                        )
                    }
                }

                AnimatedVisibility(myServiceScreenState.showSnackBarSuccess) {
                    myServiceScreenState.successMessageSnackBar?.let {
                        SnackBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                                .align(Alignment.TopCenter),
                            text = it,
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun MyServiceScreenContentPreview() {
    MyServiceScreenContent(
        myServiceScreenState = MyServiceScreenState(),
        myServiceInteractionListener = object : MyServiceInteractionListener {
            override fun onBackClick() {}
            override fun onClickSave() {}
            override fun onClickRetry() {}
            override fun onClickService(service: String) {}
        },
    )
}