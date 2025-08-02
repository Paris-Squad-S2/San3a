package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.common.LoadingScreen
import com.paris_2.san3a.presentation.screen.common.NoInternetScreen
import com.paris_2.san3a.presentation.shared.components.AppBackButton
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.OTPInputTextField
import com.paris_2.san3a.presentation.shared.components.ProgressIndicator
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OTPRegisterScreen(viewModel: OTPRegisterViewModel = koinViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    OTPRegisterScreenContent(uiState.value, viewModel)

}

@Composable
private fun OTPRegisterScreenContent(
    otpRegisterScreenState: OTPRegisterScreenState,
    otpRegisterListenerInteraction: OTPRegisterListenerInteraction,
    modifier: Modifier = Modifier
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        when {
            otpRegisterScreenState.errorMessage != null -> {
                NoInternetScreen(onClickRetry = otpRegisterListenerInteraction::onClickRetry)
            }

            otpRegisterScreenState.isLoading -> {
                LoadingScreen()
            }

            else -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                        .statusBarsPadding()
                        .navigationBarsPadding()

                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        AppBackButton(
                            onClickBackButton = otpRegisterListenerInteraction::onClickBackButton,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Image(
                            modifier = Modifier.size(200.dp),
                            painter = painterResource(R.drawable.image_otp),
                            contentDescription = stringResource(R.string.otp_image)
                        )

                    }

                    VerificationCodeContent(
                        otpRegisterScreenState.otpRegisterUiState.phoneNumber,
                        otpRegisterScreenState.otpRegisterUiState.otp,
                        otpRegisterScreenState.otpRegisterUiState.secondLeft,
                        otpRegisterScreenState.otpRegisterUiState.loadingVerifyButton,
                        otpRegisterListenerInteraction::onOtpTextChange,
                        otpRegisterListenerInteraction::onClickVerify,
                        otpRegisterListenerInteraction::onClickResendCode,
                        Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

@Composable
private fun VerificationCodeContent(
    phoneNumber: String,
    otp: String,
    secondLeft: Int,
    isLoading: Boolean,
    onOtpTextChange: (String) -> Unit,
    onClickVerify: () -> Unit,
    onClickResendCode: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Theme.colors.background.card,
                shape = RoundedCornerShape(
                    topStart = Theme.radius.quintXLarge,
                    topEnd = Theme.radius.quintXLarge,
                )
            )
            .padding(top = 40.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.enter_verification_code),
            style = Theme.textStyle.title.medium,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.we_sent_a_5_digit_code_to_your_phone_number),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.secondary,
            modifier = Modifier
        )

        Text(
            text = "$phoneNumber.",
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OTPInputTextField(
            otpText = otp,
            onOtpTextChange = { onOtpTextChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            type = AppButtonType.Primary,
            size = AppButtonSize.Large,
            state = if (otp.count() < 5) AppButtonState.Disabled else AppButtonState.Enable,
            onClick = { onClickVerify() },
            text = stringResource(R.string.verify),
        ) {
            AnimatedVisibility(isLoading) {
                ProgressIndicator()
            }
        }

        Text(
            text = stringResource(R.string.didn_t_receive_the_code),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.secondary,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        AnimatedContent(targetState = secondLeft == 0) {
            if (it) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    type = AppButtonType.Secondary,
                    size = AppButtonSize.Large,
                    onClick = { onClickResendCode() },
                    text = stringResource(R.string.resend_code),
                    enableSecondaryBackgroundColor = Theme.colors.shade.quinary,
                )
            } else {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    type = AppButtonType.Secondary,
                    size = AppButtonSize.Large,
                    state = AppButtonState.Disabled,
                    onClick = {},
                    text = stringResource(R.string.resend_code_after) + stringResource(R.string._0) + "$secondLeft s.",
                )
            }

        }


    }
}


@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun OTPRegisterScreenContentPreview() {
    OTPRegisterScreenContent(
        otpRegisterScreenState = OTPRegisterScreenState(),
        otpRegisterListenerInteraction = object : OTPRegisterListenerInteraction {
            override fun onOtpTextChange(otp: String) {

            }

            override fun onClickVerify() {

            }

            override fun onClickResendCode() {

            }

            override fun onClickBackButton() {

            }

            override fun onClickRetry() {

            }
        }
    )
}

