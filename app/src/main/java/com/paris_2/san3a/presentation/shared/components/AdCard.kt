package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AdCard(
    title: String,
    caption: String,
    buttonTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(136.dp)
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.brand.tertiary)
            .border(
                width = 2.dp,
                color = Theme.colors.brand.secondary,
                shape = RoundedCornerShape(Theme.radius.tripleXLarge)
            )
    ){
        Column {
            Row(
                modifier = Modifier.padding(top = 16.dp , start = 16.dp)
            ){
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(Theme.radius.full))
                        .background(Theme.colors.background.card)
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_sledgehammer_bold),
                        contentDescription = "AD",
                        tint = Theme.colors.brand.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier
                    )
                    Text(
                        text = caption,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.full))
                    .background(Theme.colors.button.primary)
                    .clickable{ onClick() }
            ){
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = buttonTitle,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.button.onPrimary,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right_outline),
                        contentDescription = "Arrow",
                        tint = Theme.colors.button.onPrimary,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(20.dp)
                            .padding(start = 2.5.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview(){
    AdCard(
        title = "Title",
        caption = "Caption",
        buttonTitle = "Button",
        modifier = Modifier.padding(16.dp),
        onClick = {}
    )
}