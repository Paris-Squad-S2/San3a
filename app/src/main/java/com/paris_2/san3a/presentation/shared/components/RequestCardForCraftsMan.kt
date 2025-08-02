package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import com.paris_2.san3a.presentation.shared.designSystem.radius.defaultSan3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestCardForCraftsMan(
    modifier: Modifier = Modifier,
    title : String,
    type : String,
    offers : String,
    description : String,
    location : String,
    onClick : () -> Unit
){
    Column(
        modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(defaultSan3aRadius.extraExtraLarge))
        .background(Theme.colors.background.card)
        .padding(16.dp)
        .clickable{ onClick() },
        verticalArrangement = Arrangement.Center
    )
    {
        Row {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(Theme.radius.full))
                    .background(Theme.colors.additional.primary.blue.copy(alpha = 0.08f))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_waterdrops_bold),
                    contentDescription = "",
                    tint = Theme.colors.additional.primary.blue,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                )
            }
            Column(
                modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.body.medium.semibold,
                    color = Theme.colors.shade.primary
                )
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = type,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary
                    )
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .size(3.dp)
                            .clip(RoundedCornerShape(Theme.radius.full))
                            .background(Theme.colors.shade.tertiary)
                    )
                    Text(
                        text = "$offers offers",
                        style = Theme.textStyle.body.medium.semibold,
                        color = Theme.colors.brand.primary
                    )
                }


            }

        }

//        Box(
//            modifier = Modifier
//                .padding(vertical = 16.dp)
//                .fillMaxWidth()
//                .height(1.dp)
//                .clip(RoundedCornerShape(Theme.radius.full))
//                .background(Theme.colors.shade.quaternary)
//        )
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            color = Theme.colors.shade.quaternary,
            thickness = 1.dp
        )
        Column{
            Text(
                text = description,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_outline),
                    contentDescription = "",
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = location,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }


        }


    }
}
@Preview
@Composable
fun RequestCardForCraftsManPreview() {
    RequestCardForCraftsMan(
        title = "Title",
        type = "Type",
        offers = "2",
        description = "Description",
        location = "Location",
        onClick = {}
    )
}