package com.paris_2.san3a.presentation.screen.home.craftsman.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
            .clip(RoundedCornerShape(Theme.radius.extraLarge))
            .background(Theme.colors.background.card)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatsColumn(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_suitcase,
                contentDescription = stringResource(R.string.jobs_done),
                iconTint = Theme.colors.additional.primary.purple,
                label = stringResource(R.string.jobs_done),
                value = jobsDone.toString()
            )

            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                color = Theme.colors.stroke.primary,
                thickness = 1.dp
            )

            StatsColumn(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_wallet_money,
                contentDescription = stringResource(R.string.earnings),
                iconTint = Theme.colors.additional.primary.green,
                label = stringResource(R.string.earnings),
                value = earnings.toString()+" "+stringResource(R.string.egp)
            )

            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                color = Theme.colors.stroke.primary,
                thickness = 1.dp
            )

            StatsColumn(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_star,
                contentDescription = stringResource(R.string.rating),
                iconTint = Theme.colors.additional.primary.yellow,
                label = stringResource(R.string.rating),
                value = rating.toString()
            )
        }
    }
}

@Composable
private fun StatsColumn(
    iconRes: Int,
    contentDescription: String,
    iconTint: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        Text(
            text = label,
            style = Theme.textStyle.label.medium.regular,
            color = Theme.colors.shade.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            style = Theme.textStyle.body.small.semibold,
            textAlign = TextAlign.Center,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
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