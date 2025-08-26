package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.CraftsmanAvatar
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun UserProfileSection(
    isCraftsman: Boolean,
    name: String,
    rating: Float,
    isVerify: Boolean,
    painter: Painter,
    onClickEdit: () -> Unit,
    modifier: Modifier = Modifier,
    phoneNumber: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.extraLarge))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CraftsmanAvatar(
            isVerify = isVerify,
            painter = painter
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = name,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            AnimatedContent(isCraftsman) {
                if (it) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star_bold),
                            contentDescription = stringResource(R.string.icon_start),
                            tint = Theme.colors.additional.primary.yellow,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "$rating ",
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.secondary
                        )
                    }
                } else {
                    Text(
                        text = phoneNumber,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary
                    )
                }
            }

        }

        IconButton(onClick = { onClickEdit() }) {
            Icon(
                painter = painterResource(R.drawable.ic_pen_new_round_outline),
                contentDescription = stringResource(R.string.edit_icon),
                tint = Theme.colors.shade.secondary,
            )
        }


    }
}

@PreviewMultiDevices
@Preview
@Composable
private fun UserProfileSectionPreview() {
    UserProfileSection(
        name = stringResource(R.string.mohammed_akkad),
        rating = 4.7f,
        onClickEdit = {},
        isVerify = true,
        painter = painterResource(R.drawable.person_chat),
        isCraftsman = false,
        phoneNumber = "123456789"
    )
}

@Preview
@Composable
private fun UserProfileSectionWithoutVerifyPreview() {
    UserProfileSection(
        name = stringResource(R.string.mohammed_akkad),
        rating = 4.7f,
        onClickEdit = {},
        isVerify = false,
        painter = painterResource(R.drawable.person_chat),
        isCraftsman = true,
    )
}