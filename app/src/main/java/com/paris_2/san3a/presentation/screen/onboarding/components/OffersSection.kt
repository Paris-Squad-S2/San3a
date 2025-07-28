package com.paris_2.san3a.presentation.screen.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R


@Composable
fun OffersSection(
    modifier: Modifier = Modifier,
    images: List<Painter>
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .graphicsLayer {
                clip = true
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 8f
                }
                .padding(top = 20.dp)
        ){
            Image(
                painter = images[0],
                contentDescription = "Front Offer Card",
                contentScale = ContentScale.Fit,
            )
        }
        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = -8f
                }
                .padding(
                    top = 90.dp,
                    bottom = 10.dp
                )
        ){
            Image(
                painter = images[1],
                contentDescription = "Front Offer Card",
                contentScale = ContentScale.Fit,
            )
        }
        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 4f
                }
                .padding(top = 175.dp)
        ){
            Image(
                painter = images[2],
                contentDescription = "Front Offer Card",
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OffersSectionPreview() {
    val images = listOf(
        painterResource(id = R.drawable.img_craftsman_offer3),
        painterResource(id = R.drawable.img_craftsman_offer1),
        painterResource(id = R.drawable.img_craftsman_offer2)
    )
    OffersSection(images = images)
}