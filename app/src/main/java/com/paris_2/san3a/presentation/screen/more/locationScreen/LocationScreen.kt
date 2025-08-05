package com.paris_2.san3a.presentation.screen.more.locationScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.more.components.SelectionBottomSheet
import com.paris_2.san3a.presentation.screen.more.components.SelectionItemData
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationScreen(viewModel: LocationViewModel = koinViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    LocationScreenContent(
        state = screenState.value,
        locationInteractionListener = viewModel
    )
}

@Composable
fun LocationScreenContent(
    state: LocationScreenState,
    locationInteractionListener: LocationInteractionListener,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        AppBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.my_location),
            onBackClick = locationInteractionListener::onNavigateBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.where_are_you_located),
                style = Theme.textStyle.display.xLarge,
                color = Theme.colors.shade.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.location_help_text),
                style = Theme.textStyle.body.large.regular,
                color = Theme.colors.shade.secondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                value = state.locationUiState.selectedGovernorate,
                onValueChange = {},
                readOnly = true,
                label = null,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_pin),
                        contentDescription = null,
                        tint = Theme.colors.shade.tertiary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_alt_arrow_down),
                        contentDescription = null,
                        tint = Theme.colors.shade.tertiary
                    )
                },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { locationInteractionListener.onShowGovernorateBottomSheet() }
            )


            Spacer(modifier = Modifier.height(12.dp))

            AppTextField(
                value = state.locationUiState.selectedStreet,
                onValueChange = {},
                label = null,
                keyboardOptions = KeyboardOptions.Default,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { locationInteractionListener.onShowStreetBottomSheet() }
            )

            Spacer(modifier = Modifier.weight(1f))

            AppButton(
                type = AppButtonType.Primary,
                size = AppButtonSize.Large,
                text = stringResource(R.string.save),
                onClick = locationInteractionListener::onClickSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }
    when (state.locationUiState.activeBottomSheet) {
        LocationBottomSheetType.GOVERNORATE -> {
            SelectionBottomSheet(
                title = stringResource(R.string.choose_governorate),
                isVisible = true,
                items = state.locationUiState.governorates.map {
                    SelectionItemData(
                        it,
                        showIcon = true
                    )
                },
                onDismiss = locationInteractionListener::onDismissBottomSheet,
                onItemClick = { locationInteractionListener.onAreaSelected(it) }
            )
        }

        LocationBottomSheetType.STREET -> {
            SelectionBottomSheet(
                title = stringResource(R.string.choose_district),
                isVisible = true,
                items = state.locationUiState.streets.map {
                    SelectionItemData(
                        it,
                        showIcon = false
                    )
                },
                onDismiss = locationInteractionListener::onDismissBottomSheet,
                onItemClick = { locationInteractionListener.onStreetChanged(it) }
            )
        }

        LocationBottomSheetType.NONE -> Unit
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    San3aTheme {
        LocationScreenContent(
            state = LocationScreenState(),
            locationInteractionListener = object : LocationInteractionListener {
                override fun onAreaSelected(area: String) {}
                override fun onStreetChanged(street: String) {}
                override fun onClickSave() {}
                override fun onClickRetry() {}
                override fun onNavigateBack() {}
                override fun onShowGovernorateBottomSheet() {}
                override fun onShowStreetBottomSheet() {}
                override fun onDismissBottomSheet() {}
            }
        )
    }
}