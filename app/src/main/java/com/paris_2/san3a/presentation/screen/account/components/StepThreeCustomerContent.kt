package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun StepThreeCustomerContent(
    modifier: Modifier = Modifier,
    onGetLocationClicked: () -> Unit = {},
    isBottomSheetShowed: Boolean = false,
) {
    val address by remember { mutableStateOf("Ahmed") }
    val addressInDetail by remember { mutableStateOf("") }
    Column(
        modifier = modifier.padding(16.dp)
    ) {

        AppTextField(
            value = address,
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
            governments = listOf("Cairo", "Giza"),
            isVisible = isBottomSheetShowed
        )
    }

}


@Composable
fun StepThreeCraftsmanContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        AddPhotosComponent(
            images = emptyList(),
            onAddPhotoClick = {},
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Theme.colors.stroke.primary, RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp),
        ) {
            Text(
                text = "You can mention your years of experience, tools you use, or types of jobs you usually handle.",
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun StepThreeCraftsmanContentPreview() {
    BasePreview {
        StepThreeCustomerContent()
    }
}