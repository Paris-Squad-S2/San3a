package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(Theme.colors.background.card), contentAlignment = Alignment.Center) {
        rememberInfiniteTransition().apply {
            val rotation by animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Restart
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_progress),
                contentDescription = stringResource(R.string.loading_icon),
                modifier = Modifier
                    .graphicsLayer { rotationZ = rotation }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun LoadingScreenPreview() {
    BasePreview {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
        ) {
            LoadingScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}