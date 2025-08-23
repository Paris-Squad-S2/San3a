package com.paris_2.san3a.presentation.screen.more.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun EditProfileBottomSheetContent(
    name: String,
    onNameChange: (String) -> Unit,
    profilePhotoUri: Uri?,
    onPickImageClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.your_name),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary
        )

        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = name,
            onValueChange = onNameChange,
            textColor = Theme.colors.shade.primary,
            textStyle = Theme.textStyle.body.medium.medium.copy(
                textDirection = TextDirection.Content,
            )
        )

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text =
                if (profilePhotoUri == null) stringResource(R.string.set_profile_photo)
                else stringResource(R.string.your_profile_photo),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
        )
        Box(
            modifier = Modifier
                .size(96.dp)
                .clickable(
                    onClick = onPickImageClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (profilePhotoUri != null) {
                Box(
                    modifier = Modifier.size(96.dp),
                    contentAlignment = Alignment.Center
                ) {

                    AsyncImage(
                        model = profilePhotoUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Theme.colors.shade.primary.copy(alpha = 0.06f)),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_camera_outline),
                            contentDescription = null,
                            tint = Theme.colors.button.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                val backgroundColor = Theme.colors.background.bottomSheetCard.copy(alpha = 0.1f)
                val borderColor = Theme.colors.shade.quaternary

                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(backgroundColor, CircleShape)
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val radius = (size.minDimension - strokeWidth) / 2

                            drawCircle(
                                color = borderColor,
                                radius = radius,
                                center = center,
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(12f, 15f),
                                        phase = 0f
                                    )
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera_outline),
                            tint = Theme.colors.shade.secondary,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.tap_here),
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.secondary
                        )
                    }

                }
            }
        }
    }
}

