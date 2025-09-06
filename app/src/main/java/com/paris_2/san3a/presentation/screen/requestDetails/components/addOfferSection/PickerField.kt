package com.paris_2.san3a.presentation.screen.requestDetails.components.addOfferSection

import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun PickerField(
    value: String?,
    label: String,
    placeholder: String,
    leadingIcon: Int,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AppTextField(
            value = value ?: "",
            onValueChange = {},
            label = label,
            placeholder = placeholder,
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(leadingIcon),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_alt_arrow_down),
                    contentDescription = null,
                    tint = Theme.colors.shade.secondary
                )
            }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .myClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        )
    }
}
