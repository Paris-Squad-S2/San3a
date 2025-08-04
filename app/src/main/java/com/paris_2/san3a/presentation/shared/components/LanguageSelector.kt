package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable

fun LanguageOption(
    text: String,
    flagRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (selected) {
        Theme.colors.brand.tertiary
    } else {
        Theme.colors.background.bottomSheetCard
    }

    val clickableModifier = Modifier.clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )

    val borderModifier = if (selected) Modifier.border(
        width = 1.dp,
        color = Theme.colors.brand.primary,
        shape = RoundedCornerShape(Theme.radius.large)
    ) else Modifier


    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.large))
            .then(borderModifier)
            .background(color = backgroundColor, shape = RoundedCornerShape(Theme.radius.large))
            .then(clickableModifier)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .width(26.dp)
                    .height(20.dp),
                painter = painterResource(id = flagRes),
                contentDescription = null,

                )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary
            )

        }
    }
}