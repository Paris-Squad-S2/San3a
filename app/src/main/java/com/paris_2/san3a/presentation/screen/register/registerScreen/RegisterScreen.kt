package com.paris_2.san3a.presentation.screen.register.registerScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterViewModel
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.components.Country
import com.paris_2.san3a.presentation.shared.components.CountrySelector
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = koinViewModel()){
    val uiState =viewModel.uiState.collectAsStateWithLifecycle()
    RegisterScreenContent(uiState.value,viewModel)
}

@Composable
fun RegisterScreenContent(
    registerUiState: RegisterUiState,
    registerInteractionListener: RegisterInteractionListener,

    ) {
    val scrollVerticalState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.brand.primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        TopSection()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Theme.colors.background.card)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                PhoneInputSection(
                    phoneNumber = registerUiState.phoneNumber,
                    selectedCountry = registerUiState.selectedCountry,
                    countries = registerUiState.countries,
                    isDropdownExpanded = registerUiState.isCountryDropdownExpanded,
                    interactionListener = registerInteractionListener
                )
                TermsAndConditionsText()
                Spacer(modifier = Modifier.height(24.dp))
                ActionButtonsSection(registerInteractionListener)
            }
        }

    }
}


@Composable
fun TopSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
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
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.welcome_to_san3a),
                color = Theme.colors.background.card,
                style = Theme.textStyle.title.medium
            )
        }

    }
}


@Composable
fun PhoneInputSection(
    phoneNumber: String,
    selectedCountry: Country,
    countries: List<Country>,
    isDropdownExpanded: Boolean,
    interactionListener: RegisterInteractionListener,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter your phone number to \ncontinue",
            style = Theme.textStyle.title.medium,
            color = Theme.colors.shade.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        val displayPhone = remember(phoneNumber, selectedCountry) {

            if (phoneNumber.isEmpty()) {
                selectedCountry.code + " "
            } else {
                selectedCountry.code + " " + formatPhoneNumber(phoneNumber)
            }
        }

        AppTextField(
            value = displayPhone,
            onValueChange = { newValue ->
                val countryCodeAndSpace = selectedCountry.code + " "
                val digitsOnly = if (newValue.startsWith(countryCodeAndSpace)) {
                    newValue.removePrefix(countryCodeAndSpace).filter { it.isDigit() }
                } else {
                    newValue.filter { it.isDigit() }
                }.take(11)
                interactionListener.onPhoneNumberChanged(digitsOnly)
            },
            placeholder = {
                if (phoneNumber.isEmpty()) {
                    Text(
                        text = "000 - 0000 - 000",
                        color = Theme.colors.shade.secondary
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                CountrySelector(
                    selectedCountry = selectedCountry,
                    countries = countries,
                    expanded = isDropdownExpanded,
                    onToggleDropdown = { interactionListener.onToggleCountryDropdown() },
                    onCountrySelected = { interactionListener.onCountrySelected(it) },
                    onDismissDropdown = { interactionListener.onDismissCountryDropdown() },
                )
            }
        )
    }
}

@Composable
fun TermsAndConditionsText() {
    Spacer(modifier = Modifier.height(8.dp))
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "By continuing, you agree on our ",
                style = Theme.textStyle.body.small.regular,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Terms and Conditions ",
                style = Theme.textStyle.body.small.semibold.copy(
                    color = Theme.colors.brand.primary
                ),
                textAlign = TextAlign.Center
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "and ",
                style = Theme.textStyle.body.small.regular,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Privacy Policy",
                style = Theme.textStyle.body.small.semibold.copy(
                    color = Theme.colors.brand.primary
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ActionButtonsSection(interactionListener: RegisterInteractionListener) {
    AppButton(
        type = AppButtonType.Primary,
        onClick = {interactionListener.onClickContinue()},
        state = AppButtonState.Enable,
        modifier = Modifier
            .fillMaxWidth(),
        size = AppButtonSize.Large,
        text = "Continue",
    )

    Spacer(modifier = Modifier.height(120.dp))

    Text(
        "Or",
        style = Theme.textStyle.body.medium.regular,
        color = Theme.colors.shade.secondary
    )
    Spacer(modifier = Modifier.height(16.dp))

    AppButton(
        type = AppButtonType.Secondary,
        state = AppButtonState.Enable,
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
        ,
        size = AppButtonSize.Large,
        text = "Continue as a Guest",
    )
}


@Preview(showBackground = false, showSystemUi = true)
@Composable

fun RegisterScreenPreview() {
    RegisterScreenContent(
        registerUiState = RegisterUiState(),
        registerInteractionListener = object : RegisterInteractionListener{
            override fun onCountrySelected(country: Country) {
            }

            override fun onToggleCountryDropdown() {
            }

            override fun onDismissCountryDropdown() {
            }

            override fun onPhoneNumberChanged(phone: String) {
            }

            override fun onClickContinue() {
            }

            override fun onClickContinueAsGuest() {
            }

        }
    )
}