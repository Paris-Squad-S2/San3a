package com.paris_2.san3a.presentation.screen.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.CraftsManOffer


@Composable
fun Offers(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .graphicsLayer {
                clip = false
            },
        contentAlignment = Alignment.TopCenter
    ) {
        CraftsManOffer(
            painter = painterResource(id = R.drawable.img_avatar1),
            name = "Ahmed Al-Rashid",
            rate = 4.7f,
            reviewsNumber = 121,
            description = "I can fix this today. I have 10+ years experience with kitchen plumbing.",
            amount = "50,000 IQD",
            time = "Tomorrow, 2:00 PM",
            addShadow = true,
            postedTime = "5 hour ago",
            onChatClick = {},
            onAcceptOfferClick = {},
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 8f
                }
                .offset(y = 20.dp)
        )
        CraftsManOffer(
            painter = painterResource(id = R.drawable.img_avatar2),
            name = "Muhammed Ali",
            rate = 4.8f,
            reviewsNumber = 140,
            description = "Professional plumber with quick response time. I can come today evening.",
            amount = "35,000 IQD",
            time = "Tomorrow, 3:00 PM",
            addShadow = true,
            postedTime = "7 hour ago",
            onChatClick = {},
            onAcceptOfferClick = {},
            modifier = Modifier
                .padding(top = 22.dp)
                .graphicsLayer {
                    rotationZ = -8f
                }
                .offset(y = 70.dp, x = (-8).dp)
        )
        CraftsManOffer(
            painter = painterResource(id = R.drawable.img_avatar3),
            name = "Ahmed Mosa",
            rate = 4.5f,
            reviewsNumber = 95,
            description = "Experienced in residential plumbing. Available for urgent jobs right now.",
            amount = "45,000 IQD",
            time = "Tomorrow, 1:00 PM",
            addShadow = true,
            postedTime = "2 hour ago",
            onChatClick = {},
            onAcceptOfferClick = {},
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 5f
                }
                .offset(y = 165.dp, x = 6.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun OffersPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Offers()

    }
}