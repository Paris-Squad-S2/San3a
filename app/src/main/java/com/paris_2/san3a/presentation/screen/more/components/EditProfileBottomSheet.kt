package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun EditProfileBottomSheet(
    name: String,
    imageUrl: String,
    onNameValueChange: (String) -> Unit,
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier,
    hasImage: Boolean = false
) {
    BottomSheet(
        header = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.edit_profile),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.weight(1F)
                )

                IconButton(onClick = { onClickClose() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.your_name),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.primary
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                value = name,
                onValueChange = { onNameValueChange(it) },
            )
            AnimatedContent(hasImage) {
                if (it) {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = stringResource(R.string.your_profile_photo),
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.primary
                    )

                    Image(
                        modifier = Modifier.size(96.dp),
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = stringResource(R.string.image_person)
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Set profile photo",
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.primary
                    )

                    Column(
                        modifier = Modifier.size(96.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Icon(
                            painter = painterResource(R.drawable.ic_camera_outline),
                            contentDescription = null,
                            tint = Theme.colors.shade.secondary
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(R.string.tap_here),
                            color = Theme.colors.shade.secondary,
                            style = Theme.textStyle.body.small.medium
                        )
                    }

                }
            }

        }
    }
}