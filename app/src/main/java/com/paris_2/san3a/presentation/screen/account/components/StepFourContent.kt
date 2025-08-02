package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun StepFourCustomerContent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    Column(

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppTextField(
            label = stringResource(R.string.what_s_your_name),
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.enter_your_full_name),
            modifier = modifier
        )
        Row(
            modifier = modifier,
        ) {
            Text(
                text = stringResource(R.string.set_your_profile_photo),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.primary
            )
            Text(
                text = stringResource(R.string.optional),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary
            )
        }
        AddSinglePhotoCircle(
            onTap = {}
        )
    }
}

@Composable
fun StepFourCraftsmanContent(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxWidth(),
    ){
        Text(
            text = stringResource(R.string.upload_front_of_national_id),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )
        AddPhotosComponent(
            images = emptyList(),
            onAddPhotoClick = {},
            modifier = Modifier.height(96.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.upload_back_of_national_id),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(top = 16.dp,bottom = 12.dp)
        )
        AddPhotosComponent(
            images = emptyList(),
            onAddPhotoClick = {},
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
        )
        AppButton(
            text = stringResource(R.string.i_ll_verify_later),
            onClick = {},
            modifier = Modifier.fillMaxWidth()
                .padding(top=32.dp),
            state = AppButtonState.Enable,
            type = AppButtonType.Secondary,
        )
    }
}
@Preview
@Composable
private fun StepFourCustomerContentPreview() {
    San3aTheme {
        StepFourCustomerContent()
    }
}

@Preview
@Composable
private fun StepFourCraftsmanPreview() {
    San3aTheme {
        StepFourCraftsmanContent()
    }
}