package com.paris_2.san3a.presentation.screen.account.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun ShowYourWorkContent(
    modifier: Modifier = Modifier,
    onAddWorkImagesClick: () -> Unit,
    workImages: List<Uri>? = null,
    workDescription: String,
    onDescriptionChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        AddWorkPhotosComponent(
            images = workImages,
            onAddPhotoClick = onAddWorkImagesClick,
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.describe_your_work),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.primary
            )
            Text(
                text = stringResource(R.string.optional),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary
            )
        }
        AppTextField(
            value = workDescription,
            onValueChange = onDescriptionChanged,
            placeholder = stringResource(R.string.mention_your_years_of_experience),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun StepThreeCustomerContent(
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
) {
    val address by remember { mutableStateOf("Ahmed") }
    val addressInDetail by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp)
    ) {

        AppTextField(
            value = if (government.isEmpty()) "Enter your location" else "$city, $government",
            onValueChange = { address },
            placeholder = "Get your location",
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_alt_arrow_down),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_location_pin),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            },
            enabled = false,
            modifier = Modifier.clickable(
                enabled = true, onClick = {
                    onGetLocationClicked()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
        )
        Spacer(Modifier.height(12.dp))

        AppTextField(
            value = addressInDetail,
            onValueChange = { addressInDetail },
            placeholder = "Enter your location in details",
        )
        GovernmentBottomSheet(
            governments = governments,
            isVisible = isGovernmentSheetShowed,
            onDismissRequest = onGovernmentDismissRequest,
            onClick = onGovernmentSelected
        )
        CitiesBottomSheet(
            cities = cities,
            isVisible = isCitiesSheetShowed,
            onDismissRequest = onCitiesDismissRequest,
            onClick = onCitiesSelected
        )
    }

}

@Preview
@Composable
private fun ShowYourWorkContentPreview() {
    San3aTheme {
        ShowYourWorkContent(
            modifier = Modifier,
            onAddWorkImagesClick = {},
            workImages = null,
            workDescription = "",
            onDescriptionChanged = {}
        )
    }
}


@Preview
@Composable
private fun StepThreeCraftsmanContentPreview() {
    BasePreview {
        StepThreeCustomerContent(
            governments = listOf("Cairo"),
            cities = listOf("Cairo"),
        )
    }
}