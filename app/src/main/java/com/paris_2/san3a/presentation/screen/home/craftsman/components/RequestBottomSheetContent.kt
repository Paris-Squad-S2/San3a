package com.paris_2.san3a.presentation.screen.home.craftsman.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.ProgressBar
import com.paris_2.san3a.presentation.shared.components.RequestTitleContent
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestBottomSheetContent(
    title: String,
    icon: Int,
    color: Color,
    subTitle: String,
    step: Int,
    buttonTitle: String ,
    modifier: Modifier = Modifier,
    buttonIsActive: Boolean = false,
    onClickBack: (() -> Unit)? = null,
    onButtonClick: () -> Unit,
    onExitClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Column {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
                Text(
                    text = title,
                    style = Theme.textStyle.label.medium.medium,
                    color = color
                )
            }
            Text(
                text = subTitle,
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            content()
            Row(modifier = Modifier
                .padding(top = 24.dp)
            ){
                if (onClickBack != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(Theme.radius.full))
                            .background(
                                Theme.colors.background.bottomSheetCard
                            )
                            .clickable {
                                if (buttonIsActive) { onClickBack()}
                            }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_alt_arrow_left_outline),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(Theme.radius.full))
                        .background(
                            if (buttonIsActive) Theme.colors.button.primary else Theme.colors.button.disabled
                        )
                        .clickable { onButtonClick() }
                ){
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = buttonTitle,
                            style = Theme.textStyle.body.medium.medium,
                            color = Theme.colors.button.onPrimary,
                        )
                    }
                }

            }
            ProgressBar(
                step = step
                , modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { onExitClick() }
        )

    }
}

@Preview
@Composable
private fun Preview(){
    RequestBottomSheetContent(
        title = "Plumping Request",
        icon = R.drawable.ic_waterdrops_bold,
        color = Theme.colors.shade.primary,
        subTitle = "What do you need help with?",
        buttonTitle = "Next",
        onButtonClick = {},
        onExitClick = {},
        step = 1,
    ){
        RequestTitleContent(
            value = "",
            onValueChange = {  },
            suggestions = emptyList(),
            selectedSuggestion =  "",
            onChipClick = {  }
        )
    }
}