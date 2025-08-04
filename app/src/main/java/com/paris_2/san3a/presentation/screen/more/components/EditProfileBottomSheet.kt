package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.Uri
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun EditProfileBottomSheet(
    name: String,
    onNameChange: (String) -> Unit,
    profileUri: Uri?,
    onPickImageClick: () -> Unit,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
) {
    BottomSheet(
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        header = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.close),
                        tint = Theme.colors.shade.secondary
                    )
                }
            }
        }
    ) {
        EditProfileBottomSheetContent(
            name = name,
            onNameChange = onNameChange,
            profilePhotoUri = profileUri,
            onPickImageClick = onPickImageClick
        )
    }

}