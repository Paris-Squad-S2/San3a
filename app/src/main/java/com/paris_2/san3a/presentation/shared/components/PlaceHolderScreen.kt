package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun PlaceHolderScreen(
    modifier: Modifier = Modifier,
    action: (() -> Unit)? = null,
    actionText: Int? = null,
    image: Int? = null,
    title: Int? = null,
    description: Int? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        image?.let {
            Image(
                painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
            )
        }
        title?.let {
            Text(
                text = stringResource(title),
                style = Theme.textStyle.title.small,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Theme.colors.shade.primary
            )
        }
        description?.let {
            Text(
                text = stringResource(description),
                style = Theme.textStyle.body.medium.regular,
                modifier = Modifier.width(280.dp).padding(bottom = 24.dp),
                textAlign = TextAlign.Center,
                color = Theme.colors.shade.secondary
            )
        }
        action?.let {
            actionText?.let {
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(actionText),
                    onClick = action,
                    type = AppButtonType.Primary
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PlaceHolderScreenPreview() {
    PlaceHolderScreen(
        action = {},
        actionText = com.paris_2.san3a.R.string.try_again,
        image = com.paris_2.san3a.R.drawable.img_lost_connection,
        title = com.paris_2.san3a.R.string.oops_no_internet,
        description = com.paris_2.san3a.R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background
    )
}