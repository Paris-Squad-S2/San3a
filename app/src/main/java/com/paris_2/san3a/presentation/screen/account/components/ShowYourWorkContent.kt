package com.paris_2.san3a.presentation.screen.account.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
                .fillMaxWidth(),
            singleLine = false,
            minLines = 2
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