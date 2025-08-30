package com.paris_2.san3a.presentation.screen.account.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun VerifyIdentityContent(
    modifier: Modifier = Modifier,
    onFrontOfNationalIdUploadClick: () -> Unit,
    onBackOfNationalIdUploadClick: () -> Unit,
    frontOfNationalIdUri: Uri? = null,
    backOfNationalIdUri: Uri? = null,
    onVerifyLaterClick: () -> Unit,
    verifyButtonState: AppButtonState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(R.string.upload_front_of_national_id),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        AddSingleDocumentPhoto(
            imageUri = frontOfNationalIdUri,
            onAddPhotoClick = onFrontOfNationalIdUploadClick,
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.upload_back_of_national_id),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )
        AddSingleDocumentPhoto(
            imageUri = backOfNationalIdUri,
            onAddPhotoClick = onBackOfNationalIdUploadClick,
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
        )
        AppButton(
            text = stringResource(R.string.i_ll_verify_later),
            onClick = onVerifyLaterClick,
            size = AppButtonSize.Large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            state = verifyButtonState,
            type = AppButtonType.Secondary,
            loadingIcon = {
                LoadingScreen(
                    modifier = Modifier.size(25.dp),
                    background = Theme.colors.button.secondary
                )
            }
        )
    }
}

@Preview
@Composable
private fun VerifyIdentityContentPreview() {
    San3aTheme {
        VerifyIdentityContent(
            onFrontOfNationalIdUploadClick = {},
            onBackOfNationalIdUploadClick = {},
            onVerifyLaterClick = {},
            verifyButtonState = AppButtonState.Enable
        )
    }
}