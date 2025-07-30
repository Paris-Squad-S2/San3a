package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun CategoryItem(
    title: String,
    description: String,
    tint: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    isLarge: Boolean = true,
    painter: Painter = painterResource(id = R.drawable.ic_arrow_left_outline),
){
    if (isLarge){
        CategoryItemLarge(
            title = title,
            description = description,
            tint = tint,
            iconColor = iconColor,
            modifier = modifier,
            painter = painter
        )
    }else{
        CategoryItemSmall(
            title = title,
            description = description,
            tint = tint,
            iconColor = iconColor,
            modifier = modifier,
            painter = painter
        )
    }
}

@Composable
fun CategoryItemLarge(
    title: String,
    description: String,
    tint: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.ic_arrow_left_outline),
){
    Box(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
    ){
        Column(
            modifier = Modifier.padding(start = 16.dp , top = 16.dp , bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
                    .background(iconColor)
            ) {
                Icon(
                    painter = painter,
                    contentDescription = "Category Icon",
                    tint = tint,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Text(
                text = title,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
            )
        }
    }
}

@Composable
fun CategoryItemSmall(
    title: String,
    description: String,
    tint: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.ic_arrow_left_outline),
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
    ){
        Row(
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
                    .background(iconColor)
            ){
                Icon(
                    painter = painter,
                    contentDescription = "Category Icon",
                    tint = tint,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.primary
                )
                Text(
                    text = description,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.secondary
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview(){
    CategoryItem(
        title = "Title",
        description = "Description",
        tint = Theme.colors.additional.primary.blue,
        iconColor = Theme.colors.additional.secondary.blue,
        painter = painterResource(id = R.drawable.ic_arrow_left_outline)
    )
}