package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun CategoryItem(
    title: String,
    description: String,
    tint: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    isLarge: Boolean = true,
    painter: Painter,
    onclick: () -> Unit
){
    if (isLarge){
        CategoryItemLarge(
            title = title,
            description = description,
            tint = tint,
            iconColor = iconColor,
            modifier = modifier,
            painter = painter,
            onclick = onclick
        )
    }else{
        CategoryItemSmall(
            title = title,
            description = description,
            tint = tint,
            iconColor = iconColor,
            modifier = modifier,
            painter = painter,
            onclick = onclick
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
    painter: Painter,
    onclick: () -> Unit
){
    Box(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
            .clickable { onclick() }
    ){
        Column(
            modifier = Modifier
                .padding(start = 16.dp , top = 16.dp , bottom = 16.dp)
        ) {
            CategoryIconBox(
                painter = painter,
                iconColor = iconColor,
                tint = tint,
                modifier = Modifier.padding(bottom = 12.dp)
            )
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
    painter: Painter,
    onclick: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
            .clickable { onclick() }
    ){
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            CategoryIconBox(
                painter = painter,
                iconColor = iconColor,
                tint = tint
            )

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
@PreviewLightDark
@Composable
private fun Preview(){
    San3aTheme{
        CategoryItem(
            title = "Title",
            description = "Description",
            tint = Theme.colors.additional.primary.blue,
            iconColor = Theme.colors.additional.secondary.blue,
            painter = painterResource(R.drawable.ic_conditioner_bold),
            onclick = {}
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewSmall(){
    San3aTheme{
        CategoryItem(
            title = "Title",
            description = "Description",
            tint = Theme.colors.additional.primary.blue,
            iconColor = Theme.colors.additional.secondary.blue,
            isLarge = false,
            painter = painterResource(R.drawable.ic_conditioner_bold),
            onclick = {}
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewLargeAC(){
    San3aTheme{
        CategoryItem(
            title = "Title",
            description = "Description",
            tint = Theme.colors.additional.primary.red,
            iconColor = Theme.colors.additional.secondary.red,
            painter = painterResource(R.drawable.ic_washing_machine_bold),
            onclick = {}
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewSmallAC(){
    San3aTheme{
        CategoryItem(
            title = "Title",
            description = "Description",
            tint = Theme.colors.additional.primary.red,
            iconColor = Theme.colors.additional.secondary.red,
            isLarge = false,
            painter = painterResource(R.drawable.ic_washing_machine_bold),
            onclick = {}
        )
    }
}