package com.paris_2.san3a.presentation.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.paris_2.san3a.R

@Composable
fun LostConnectionScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaceHolderScreen(
        modifier = modifier,
        image = R.drawable.img_lost_connection,
        title = R.string.oops_no_internet,
        description = R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background,
        action = onRetry,
        actionText = R.string.try_again
    )
}

@PreviewLightDark
@Composable
fun LostConnectionScreenPreview() {
    LostConnectionScreen(onRetry = {})
}