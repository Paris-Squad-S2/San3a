package com.paris_2.san3a.presentation.utill

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun Modifier.myClickable(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = MutableInteractionSource(),
    rippleColor: Color = Theme.colors.shade.primary,
    rippleRadius: Dp = Dp.Unspecified,
    bounded: Boolean = true,
    indication: Indication? = ripple(
        color = rippleColor,
        bounded = bounded,
        radius = rippleRadius
    ),
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = this
    .clickable(
        enabled = enabled,
        interactionSource = remember { interactionSource },
        indication = indication,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
