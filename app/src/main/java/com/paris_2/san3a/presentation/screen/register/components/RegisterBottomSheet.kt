package com.paris_2.san3a.presentation.screen.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R

@Composable
fun RegisterBottomSheet(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    headerText : String,
    contentText : String
){
    BottomSheet(
        header = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = headerText,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small
                )
                IconButton(onClick = onCloseClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = contentText,
                style = Theme.textStyle.body.medium.medium
            )
            AppButton(
                type = AppButtonType.Secondary,
                state = AppButtonState.Enable,
                onClick = onCloseClick,
                modifier = Modifier.fillMaxWidth(),
                size = AppButtonSize.Large,
                text = stringResource(R.string.ok),
            )
        }
    }
}