package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview


@Composable
fun LocationContent(
    modifier: Modifier = Modifier,
    onGetLocationClicked: () -> Unit = {},
    isGovernmentSheetShowed: Boolean = false,
    isCitiesSheetShowed: Boolean = false,
    onGovernmentDismissRequest: () -> Unit = {},
    onCitiesDismissRequest: () -> Unit = {},
    governments: List<String>,
    cities: List<String>,
    onGovernmentSelected: (String) -> Unit = {},
    onCitiesSelected: (String) -> Unit = {},
    government: String = "",
    city: String = "",
    addressInDetails: String,
    onAddressDetailsChange: (String) -> Unit = {},
    locationBottomSheetContentType: LocationBottomSheetContentType
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {

        AppTextField(
            value = if (government.isEmpty()) stringResource(R.string.enter_your_location)
            else "$city, $government",
            onValueChange = { "address" },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_alt_arrow_down),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_location_pin),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            },
            enabled = false,
            modifier = Modifier.clickable(
                onClick = {
                    onGetLocationClicked()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
        )
        Spacer(Modifier.height(12.dp))

        AppTextField(
            value = addressInDetails,
            onValueChange = onAddressDetailsChange,
            placeholder = stringResource(R.string.enter_your_location_in_details),
        )
        GovernmentBottomSheet(
            governments = governments,
            cities = cities,
            isVisible = isGovernmentSheetShowed,
            onDismissRequest = onGovernmentDismissRequest,
            locationBottomSheetType = locationBottomSheetContentType,
            onGovernmentClick = onGovernmentSelected,
            onCityClick = onCitiesSelected
        )

    }
}

@Preview
@Composable
private fun LocationContentPreview() {
    BasePreview {
        LocationContent(
            governments = listOf("Cairo"),
            cities = listOf("Cairo"),
            addressInDetails = "AddressPreveiw",
            locationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT
        )
    }
}