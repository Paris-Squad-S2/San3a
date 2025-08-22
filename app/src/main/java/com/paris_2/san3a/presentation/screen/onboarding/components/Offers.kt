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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.CraftsManOffer
import com.paris_2.san3a.presentation.shared.components.OfferDetailsUIState
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import com.paris_2.san3a.presentation.utill.minusNHours
import com.paris_2.san3a.presentation.utill.plusNDays
import com.paris_2.san3a.presentation.utill.plusNHours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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
            onSecondaryButtonClick = {},
            onPrimaryButtonClick = {},
            modifier = Modifier
                .graphicsLayer { rotationZ = 8f }
                .offset(y = 20.dp),
            offerDetails = OfferDetailsUIState(
                name = stringResource(R.string.ahmed_al_rashid),
                rate = 4.7f,
                reviewsNumber = 121,
                description = stringResource(R.string.i_can_fix_this_today_i_have_10_years_experience_with_kitchen_plumbing),
                amount = stringResource(R.string._50_000_iqd),
                dateTime = getCurrentDateTime().plusNDays(1).plusNHours(1),
                postedTime = getCurrentDateTime().minusNHours(5),
            ),
            painter = painterResource(id = R.drawable.img_avatar1),
            addShadow = true,
            forCraftsMan = false,
        )
        CraftsManOffer(
            onSecondaryButtonClick = {},
            onPrimaryButtonClick = {},
            modifier = Modifier
                .padding(top = 22.dp)
                .graphicsLayer { rotationZ = -8f }
                .offset(y = 70.dp, x = (-8).dp),
            offerDetails = OfferDetailsUIState(
                name = stringResource(R.string.muhammed_ali),
                rate = 4.8f,
                reviewsNumber = 140,
                description = stringResource(R.string.professional_plumber_with_quick_response_time_i_can_come_today_evening),
                amount = stringResource(R.string._35_000_iqd),
                dateTime = getCurrentDateTime().plusNDays(1).plusNHours(2),
                postedTime = getCurrentDateTime().minusNHours(7),
            ),
            painter = painterResource(id = R.drawable.img_avatar2),
            addShadow = true,
            forCraftsMan = false,
        )
        CraftsManOffer(
            onSecondaryButtonClick = {},
            onPrimaryButtonClick = {},
            modifier = Modifier
                .graphicsLayer { rotationZ = 5f }
                .offset(y = 165.dp, x = 6.dp),
            offerDetails = OfferDetailsUIState(
                name = stringResource(R.string.ahmed_mosa),
                rate = 4.5f,
                reviewsNumber = 95,
                description = stringResource(R.string.experienced_in_residential_plumbing_available_for_urgent_jobs_right_now),
                amount = stringResource(R.string._45_000_iqd),
                dateTime = getCurrentDateTime().plusNDays(1),
                postedTime = getCurrentDateTime().minusNHours(2),
            ),
            painter = painterResource(id = R.drawable.img_avatar3),
            addShadow = true,
            forCraftsMan = false,
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