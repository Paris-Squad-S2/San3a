package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun StepFourContent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppTextField(
            label = stringResource(R.string.what_s_your_name),
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.enter_your_full_name),
        )
        Row {
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

@Preview
@Composable
private fun StepFurContentPreview() {
    San3aTheme {
        StepFourContent()
    }

}