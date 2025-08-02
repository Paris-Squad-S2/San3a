package com.paris_2.san3a.presentation.screen.account.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    onAddPhotoClick: () -> Unit,
    profilePhotoUri: Uri?,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppTextField(
            label = stringResource(R.string.what_s_your_name),
            value = name,
            onValueChange = onNameChanged,
            placeholder = stringResource(R.string.enter_your_full_name),
            modifier = Modifier.fillMaxWidth()
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
            modifier = modifier.padding(bottom = 32.dp),
            onTap = onAddPhotoClick,
            imageUri = profilePhotoUri
        )
    }
}

@Preview
@Composable
private fun StepFourCustomerContentPreview() {
    San3aTheme {
        ProfileContent(
            name = "John Doe",
            onNameChanged = {},
            profilePhotoUri = null,
            onAddPhotoClick = {}
        )
    }
}