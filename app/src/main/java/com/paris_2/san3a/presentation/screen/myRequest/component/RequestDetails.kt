package com.paris_2.san3a.presentation.screen.myRequest.component

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

data class OrderTrackItem(
    @StringRes val status: Int,
    val isDone: Boolean,
    val icon: Int = R.drawable.ic_clipboard
)

fun orderTrackItems(currentStep: Int): List<OrderTrackItem> {
    val statuses = listOf(
        R.string.submitted,
        R.string.receiving_offers,
        R.string.craftsman_selected,
        R.string.in_progress,
        R.string.done
    )

    return statuses.mapIndexed { index, status ->
        OrderTrackItem(
            status = status,
            isDone = index <= currentStep,
        )
    }
}

@Composable
fun RequestDetails(currentStep: Int = 0) {
    Row {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val items = orderTrackItems(currentStep)

            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val backgroundIcon = animateColorAsState(
                        if (item.isDone) Theme.colors.background.card
                        else Theme.colors.shade.tertiary
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = animateColorAsState(
                                    if (item.isDone) Theme.colors.brand.primary
                                    else Theme.colors.button.disabled
                                ).value,
                                shape = RoundedCornerShape(Theme.radius.full)
                            )
                            .clip(RoundedCornerShape(Theme.radius.full)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = backgroundIcon.value,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    val textColor = animateColorAsState(
                        if(item.isDone) Theme.colors.brand.primary else Theme.colors.shade.tertiary
                    )
                    Text(
                        text = stringResource(item.status),
                        style = Theme.textStyle.body.small.semibold,
                        color = textColor.value,
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    )


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = animateColorAsState(
                                        if (item.isDone) Theme.colors.brand.primary
                                        else Theme.colors.button.disabled
                                    ).value,
                                    shape = RoundedCornerShape(Theme.radius.full)
                                )
                                .clip(RoundedCornerShape(Theme.radius.full)),
                        )
                        if(index < items.size - 1) {
                            VerticalDivider(
                                modifier = Modifier.size(width = 2.dp, height = 40.dp),
                                color = animateColorAsState(
                                    if (item.isDone) Theme.colors.brand.primary
                                    else Theme.colors.shade.quaternary
                                ).value,
                                thickness = 2.dp
                            )
                        }

                    }



                }



            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RequestDetailsPreview() {
    RequestDetails()
}
