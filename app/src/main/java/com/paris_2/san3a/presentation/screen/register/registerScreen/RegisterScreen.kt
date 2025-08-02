package com.paris_2.san3a.presentation.screen.register.registerScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = koinViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    RegisterScreenContent(uiState.value, viewModel)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreenContent(
    registerUiState: RegisterUiState,
    registerInteractionListener: RegisterInteractionListener,
) {
    val isKeyboardVisible = WindowInsets.isImeVisible

    val topSectionHeight by animateDpAsState(
        targetValue = if (isKeyboardVisible) 172.dp else 240.dp,
        animationSpec = tween(durationMillis = 300),
        label = "topSectionHeight"
    )

    val logoSize by animateDpAsState(
        targetValue = 64.dp,
        animationSpec = tween(durationMillis = 300),
        label = "logoSize"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.brand.primary)
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSection(
                height = topSectionHeight,
                logoSize = logoSize,
                showTitle = true,
                isKeyboardVisible = isKeyboardVisible
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
                color = Theme.colors.background.card
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    PhoneNumberInput(
                        phoneNumber = registerUiState.phoneNumber,
                        onPhoneNumberChanged = { registerInteractionListener.onPhoneNumberChanged(it) },
                    )

                    TermsAndConditionsText()

                    Spacer(modifier = Modifier.height(24.dp))

                    AppButton(
                        type = AppButtonType.Primary,
                        onClick = { registerInteractionListener.onClickContinue() },
                        state = AppButtonState.Enable,
                        modifier = Modifier.fillMaxWidth(),
                        size = AppButtonSize.Large,
                        text = stringResource(R.string.Continue),
                    )

                    if (!isKeyboardVisible) {
                        Spacer(modifier = Modifier.weight(1f))
                        GuestButtonSection(registerInteractionListener)
                        Spacer(modifier = Modifier.padding(bottom = 24.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun TopSection(
    height: Dp,
    logoSize: Dp,
    showTitle: Boolean,
    isKeyboardVisible: Boolean,
) {
    Spacer(modifier = Modifier.height(68.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Theme.colors.brand.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_white_background),
                contentDescription = "Register Background",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(logoSize)
            )

            if (showTitle) {
                Spacer(modifier = Modifier.height(if (isKeyboardVisible) 8.dp else 16.dp))
                Text(
                    text = stringResource(R.string.welcome_to_san3a),
                    color = Theme.colors.background.card,
                    style = Theme.textStyle.title.large
                )
            }
        }
    }
}

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background.card)

    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.enter_your_phone_number_to_continue),
            style = Theme.textStyle.title.medium,
            color = Theme.colors.shade.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        val dialCode = "+20"
        val numberOnly = phoneNumber.removePrefix(dialCode)

        AppTextField(
            value = numberOnly,
            onValueChange = { newInput ->
                val digitsOnly = newInput.filter { it.isDigit() }.take(10)
                onPhoneNumberChanged(dialCode + digitsOnly)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = "000 - 000 - 0000",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            visualTransformation = PhoneNumberVisualTransformation(), // formats as 000-000-0000
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(Theme.colors.background.card)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_eg_flag),
                        contentDescription = "Egypt Flag",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(20.dp)
                            .height(15.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Divider(
                        color = Theme.colors.stroke.primary,
                        modifier = Modifier
                            .height(24.dp)
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = dialCode,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        )


    }
}


@Composable
fun TermsAndConditionsText() {
    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.by_continuing_you_agree_on_our))
            withStyle(
                style = SpanStyle(
                    color = Theme.colors.brand.primary,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(stringResource(R.string.terms_and_conditions))
            }
            append(stringResource(R.string.and))
            withStyle(
                style = SpanStyle(
                    color = Theme.colors.brand.primary,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(stringResource(R.string.privacy_policy))
            }
        },
        style = Theme.textStyle.body.small.regular,
        color = Theme.colors.shade.secondary,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}

@Composable
fun GuestButtonSection(interactionListener: RegisterInteractionListener) {
    Text(
        stringResource(R.string.or),
        style = Theme.textStyle.body.medium.regular,
        color = Theme.colors.shade.secondary
    )

    Spacer(modifier = Modifier.height(14.dp))

    AppButton(
        type = AppButtonType.Secondary,
        state = AppButtonState.Enable,
        onClick = interactionListener::onClickContinueAsGuest,
        modifier = Modifier.fillMaxWidth(),
        size = AppButtonSize.Large,
        text = stringResource(R.string.continue_as_a_guest),
    )
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent(
        registerUiState = RegisterUiState(),
        registerInteractionListener = object : RegisterInteractionListener {
            override fun onPhoneNumberChanged(phone: String) {}
            override fun onClickContinue() {}
            override fun onClickContinueAsGuest() {}
        }
    )
}