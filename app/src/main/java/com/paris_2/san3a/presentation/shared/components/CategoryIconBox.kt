package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun CategoryIconBox(
    painter: Painter,
    iconColor: Color,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(Theme.radius.extraLarge))
            .background(iconColor)
    ) {
        Icon(
            painter = painter,
            contentDescription = stringResource(R.string.category_item),
            tint = tint,
            modifier = Modifier
                .align(Alignment.Center)
                .size(20.dp),
        )
    }
}

@PreviewLightDark
@Composable
fun CategoryIconBoxPreview() {
    CategoryIconBox(
        tint = getResourceTint("00FoToEQx8OAVK8fprck"),
        iconColor = getResourceColors("00FoToEQx8OAVK8fprck"),
        painter = painterResource(getResource("00FoToEQx8OAVK8fprck")),
    )
}
