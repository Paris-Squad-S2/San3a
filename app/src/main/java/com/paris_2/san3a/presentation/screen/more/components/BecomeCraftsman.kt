package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun BecomeCraftsman(
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(142.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(Theme.radius.extraExtraLarge),
                color = Theme.colors.brand.secondary,
            )
            .background(
                color = Theme.colors.brand.tertiary,
                shape = RoundedCornerShape(Theme.radius.extraExtraLarge)
            )
            .padding(16.dp),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Theme.colors.background.card,
                        shape = RoundedCornerShape(Theme.radius.full)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sledgehammer_bold),
                    contentDescription = stringResource(R.string.sledgehammer_icon),
                    tint = Theme.colors.brand.primary
                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.got_a_skill_start_earning),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier
                )
                Text(
                    text = "Create your craftsman account and get\njob requests.",
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier
                )
            }
        }
        AppButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            type = AppButtonType.Primary,
            size = AppButtonSize.Small,
            state = AppButtonState.Enable,
            text = "Become a Craftsman",
            onClick = { onClickButton() },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_outline),
                    contentDescription = stringResource(R.string.become_a_craftsman_icon),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                        .offset(y = 3.dp)
                )
            }
        )
    }
}