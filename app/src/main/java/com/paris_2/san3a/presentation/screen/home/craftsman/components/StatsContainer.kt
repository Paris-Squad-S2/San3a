package com.paris_2.san3a.presentation.screen.home.craftsman.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun StatsContainer(
    jobsDone: Int,
    earnings: Double,
    rating: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(94.dp)
            .clip(RoundedCornerShape(Theme.radius.extraLarge))
            .background(Theme.colors.background.card)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_suitcase),
                    contentDescription = "Jobs Done",
                    tint = Theme.colors.additional.primary.purple,
                    modifier = Modifier.padding(bottom = 8.dp , top = 16.dp)
                )
                Text(
                    text = "Jobs Done",
                    style = Theme.textStyle.label.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier.padding(bottom = 4.dp)

                )
                Text(
                    text = "$jobsDone",
                    style = Theme.textStyle.body.small.semibold,
                    color = Theme.colors.shade.primary
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 12.dp),
                color = Theme.colors.stroke.primary,
                thickness = 1.dp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_wallet_money),
                    contentDescription = "Jobs Done",
                    tint = Theme.colors.additional.primary.green,
                    modifier = Modifier.padding(bottom = 8.dp , top = 16.dp)
                )
                Text(
                    text = "Earnings",
                    style = Theme.textStyle.label.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier.padding(bottom = 4.dp)

                )
                Text(
                    text = "$earnings IQD",
                    style = Theme.textStyle.body.small.semibold,
                    color = Theme.colors.shade.primary
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 12.dp),
                color = Theme.colors.stroke.primary,
                thickness = 1.dp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "Jobs Done",
                    tint = Theme.colors.additional.primary.yellow,
                    modifier = Modifier.padding(bottom = 8.dp , top = 16.dp)
                )
                Text(
                    text = "Rating",
                    style = Theme.textStyle.label.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier.padding(bottom = 4.dp)

                )
                Text(
                    text = "$rating",
                    style = Theme.textStyle.body.small.semibold,
                    color = Theme.colors.shade.primary
                )
            }
        }
    }
}

@Preview
//@PreviewLightDark
@Composable
private fun Preview(){
    San3aTheme {
        StatsContainer(
            jobsDone = 24,
            earnings = 720000.0,
            rating = 4.8f,
        )
    }
}
