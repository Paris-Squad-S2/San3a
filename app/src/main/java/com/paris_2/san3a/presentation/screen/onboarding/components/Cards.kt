package com.paris_2.san3a.presentation.screen.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme


data class CardItem(
    val title: String,
    val iconResId: Int,
    val backgroundColor: Color,
    val iconBackgroundColor: Color,
    val textColor: Color,
    val iconColor: Color,
)

@Composable
fun Cards() {
    val cards = listOf(
        CardItem(stringResource(id = R.string.cleaning), R.drawable.ic_washing_machine_bold, backgroundColor = Theme.colors.additional.primary.purple, textColor = Theme.colors.background.card, iconColor = Theme.colors.additional.primary.purple, iconBackgroundColor = Theme.colors.background.card),
        CardItem(stringResource(id = R.string.ac_repair), R.drawable.ic_conditioner_bold, Theme.colors.background.card, Theme.colors.additional.secondary.red, Theme.colors.shade.primary, Theme.colors.additional.primary.red),
        CardItem(stringResource(id = R.string.plumping), R.drawable.ic_waterdrops_bold, Theme.colors.background.card, Theme.colors.additional.secondary.blue, Theme.colors.shade.primary, Theme.colors.additional.primary.blue),
        CardItem(stringResource(id = R.string.electrical), R.drawable.ic_plug_circle_bold, Theme.colors.additional.primary.turquoise, Theme.colors.background.card, Theme.colors.background.card, Theme.colors.additional.primary.turquoise),
        CardItem(stringResource(id = R.string.painting), R.drawable.ic_paint_roller_bold, Theme.colors.additional.primary.yellow, Theme.colors.background.card, Theme.colors.background.card, Theme.colors.additional.primary.yellow),
        CardItem(stringResource(id = R.string.landscaping), R.drawable.ic_leaf_bold, Theme.colors.background.card, Theme.colors.additional.secondary.green, Theme.colors.shade.primary, Theme.colors.additional.primary.green),
        CardItem(stringResource(id = R.string.cleaning), R.drawable.ic_washing_machine_bold, Theme.colors.background.card, Theme.colors.additional.secondary.purple, Theme.colors.shade.primary, Theme.colors.additional.primary.purple),
        CardItem(stringResource(id = R.string.ac_repair), R.drawable.ic_conditioner_bold, Theme.colors.additional.primary.red, Theme.colors.background.card, Theme.colors.background.card, Theme.colors.additional.primary.red)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background.screen)
    ) {
        cards.chunked(2).forEachIndexed { index, pair ->
            val card1 = pair[0]
            val card2 = pair[1]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (index == 0) 0.dp else 16.dp
                    )
                    .offset(x = if (index % 2 == 0) 16.dp else (-16).dp),
                horizontalArrangement = if (index % 2 != 0) Arrangement.End else Arrangement.Start
            ) {
                OnBoardingCard(
                    title = card1.title,
                    icon = painterResource(id = card1.iconResId),
                    backgroundColor = card1.backgroundColor,
                    textColor = card1.textColor,
                    iconColor = card1.iconColor,
                    iconBackgroundColor = card1.iconBackgroundColor,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OnBoardingCard(
                    title = card2.title,
                    icon = painterResource(id = card2.iconResId),
                    backgroundColor = card2.backgroundColor,
                    textColor = card2.textColor,
                    iconColor = card2.iconColor,
                    iconBackgroundColor = card2.iconBackgroundColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardsPreview() {
    Cards()
}

