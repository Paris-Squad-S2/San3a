package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AppChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    hasBorder: Boolean = false,
    unSelectedColor: Color = Color.Unspecified,
    selectedColor: Color = Color.Unspecified,
    hasBackgroundColor: Boolean = false,
    painter: Painter? = null,
    iconContentDescription: String? = null
) {
    val borderColor =
        animateColorAsState(if (isSelected) Theme.colors.brand.secondary else Color.Unspecified)
    val borderWidth = animateDpAsState(if (isSelected) 1.dp else 0.dp)

    val backgroundColor =
        animateColorAsState(if (isSelected) selectedColor else unSelectedColor).value

    val border = if (hasBorder) Modifier.border(
        width = borderWidth.value,
        color = borderColor.value,
        shape = CircleShape
    ) else Modifier

    val hasBackgroundColorBox =
        if (hasBackgroundColor) Modifier.background(backgroundColor, CircleShape)
        else Modifier
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(36.dp)
            .then(hasBackgroundColorBox)
            .clip(CircleShape)
            .then(border)
            .clickable(indication = null, interactionSource = null) { onClick() }
    ) {
        painter?.let {
            Icon(
                painter = it,
                contentDescription = iconContentDescription,
                tint = Theme.colors.shade.primary,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        TextChips(
            label,
            isSelected,
            hasBackgroundColor,
        )
    }
}

@Composable
private fun TextChips(
    label: String,
    isSelected: Boolean,
    hasBackgroundColor: Boolean,
    modifier: Modifier = Modifier
) {

    val textColor =
        animateColorAsState(if (hasBackgroundColor) Theme.colors.background.card else Theme.colors.brand.primary)

    AnimatedContent(isSelected) {
        if (it) {
            Text(
                modifier = modifier
                    .padding(horizontal =16.dp),
                text = label,
                style = Theme.textStyle.label.medium.medium,
                color = textColor.value
            )
        } else {
            Text(
                modifier = modifier
                    .padding(horizontal =16.dp),
                text = label,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.shade.secondary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AppChipPreview() {
    San3aTheme {
        AppChip(
            onClick = {},
            label = "Label",
            isSelected = true,
            hasBorder = true,
            hasBackgroundColor = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppChipWithIconPreview() {
    San3aTheme {
        AppChip(
            onClick = {},
            label = "Label",
            isSelected = true,
            painter = painterResource(R.drawable.ic_arrow_right_outline),
            hasBorder = true,
            hasBackgroundColor = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppChipUnSelectedPreview() {
    San3aTheme {
        AppChip(
            onClick = {},
            label = "Label",
            isSelected = false,
            hasBorder = true,
            hasBackgroundColor = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppChipSelectedWithoutBorderPreview() {
    San3aTheme {
        AppChip(
            onClick = {},
            label = "Label",
            isSelected = true,
            hasBorder = false,
            unSelectedColor = Theme.colors.background.card,
            selectedColor = Theme.colors.additional.primary.turquoise,
            hasBackgroundColor = true
        )
    }
}