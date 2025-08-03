package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun CraftsmanAvatar(
    painter: Painter,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.image_profile),
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )


        Icon(
            painter = painterResource(R.drawable.ic_verified_check_bold),
            contentDescription = stringResource(R.string.verified_check),
            modifier = Modifier
                .size(16.dp)
                .graphicsLayer {
                    translationY = 75f
                },
            tint = Theme.colors.additional.primary.success
        )


    }
}

@Composable
@PreviewMultiDevices
@Preview
private fun CraftsmanAvatarPreview() {
    BasePreview {
        CraftsmanAvatar(painter = painterResource(R.drawable.person_chat))

    }
}