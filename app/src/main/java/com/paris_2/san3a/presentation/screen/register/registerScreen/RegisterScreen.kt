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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.register.components.BottomSheetType
import com.paris_2.san3a.presentation.screen.register.components.RegisterBottomSheet
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    registerUiState: RegisterUiState,
    registerInteractionListener: RegisterInteractionListener,
) {
    val isKeyboardVisible = WindowInsets.isImeVisible
    var bottomSheetType by remember { mutableStateOf<BottomSheetType?>(null) }

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

                    TermsAndConditionsText(
                        onTermsClick = { bottomSheetType = BottomSheetType.Terms },
                        onPrivacyClick = { bottomSheetType = BottomSheetType.Privacy }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppButton(
                        type = AppButtonType.Primary,
                        onClick = { registerInteractionListener.onClickContinue() },
                        state = if (registerUiState.isPhoneValid) AppButtonState.Enable else AppButtonState.Disabled,
                        modifier = Modifier.fillMaxWidth(),
                        size = AppButtonSize.Large,
                        text = stringResource(R.string.Continue),
                    )

                }
            }
        }

        bottomSheetType?.let { type ->
            when (type) {
                BottomSheetType.Terms -> {
                    RegisterBottomSheet(
                        onCloseClick = { bottomSheetType = null },
                        headerText = stringResource(R.string.terms_and_conditions),
                        contentText = stringResource(R.string.terms_and_conditions_content),
                        skipPartiallyExpanded = false
                    )
                }

                BottomSheetType.Privacy -> {
                    RegisterBottomSheet(
                        onCloseClick = { bottomSheetType = null },
                        headerText = stringResource(R.string.privacy_policy),
                        contentText = stringResource(R.string.privacy_and_policy_content),
                        skipPartiallyExpanded = false
                    )
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
) {
    Spacer(modifier = Modifier.height(68.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Theme.colors.brand.primary),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_white_background),
                    contentDescription = "Register Background",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(logoSize)
                )

                if (showTitle) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.welcome_to_san3a),
                        color = Theme.colors.background.card,
                        style = Theme.textStyle.title.large
                    )
                }
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

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            AppTextField(
                value = numberOnly,
                onValueChange = { newInput ->
                    val digitsOnly = newInput.filter { it.isDigit() }.take(10)
                    onPhoneNumberChanged(dialCode + digitsOnly)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "000 - 000 - 0000",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = PhoneNumberVisualTransformation(),
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
}

@Composable
fun TermsAndConditionsText(
    onTermsClick: (() -> Unit)? = null,
    onPrivacyClick: (() -> Unit)? = null,
) {
    Spacer(modifier = Modifier.height(8.dp))

    val termsText = stringResource(R.string.terms_conditions)
    val privacyText = stringResource(R.string.privacy_policy)
    val prefixText = stringResource(R.string.by_continuing_you_agree_on_our)
    val andText = stringResource(R.string.and)

    val annotatedString = buildAnnotatedString {
        append(prefixText)

        val termsStart = length
        withStyle(
            style = SpanStyle(
                color = Theme.colors.brand.primary,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(termsText)
        }
        val termsEnd = length
        addStringAnnotation(
            tag = "TERMS",
            annotation = "terms_and_conditions",
            start = termsStart,
            end = termsEnd
        )

        append(" $andText ")

        val privacyStart = length
        withStyle(
            style = SpanStyle(
                color = Theme.colors.brand.primary,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(privacyText)
        }
        val privacyEnd = length
        addStringAnnotation(
            tag = "PRIVACY",
            annotation = "privacy_policy",
            start = privacyStart,
            end = privacyEnd
        )
    }

    ClickableText(
        text = annotatedString,
        style = Theme.textStyle.body.small.regular.copy(
            color = Theme.colors.shade.secondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "TERMS",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onTermsClick?.invoke()
                return@ClickableText
            }

            annotatedString.getStringAnnotations(
                tag = "PRIVACY",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onPrivacyClick?.invoke()
                return@ClickableText
            }
        }
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
        }
    )
}