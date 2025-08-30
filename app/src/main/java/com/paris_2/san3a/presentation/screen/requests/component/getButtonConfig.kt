package com.paris_2.san3a.presentation.screen.requests.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.requests.customer.MyRequestCustomerUi

data class ButtonConfig(
    val text: String? = null,
    val icon: ImageVector? = null
)

@Composable
fun getButtonConfig(requestUi: MyRequestCustomerUi): ButtonConfig {
    return when (requestUi.status) {
        RequestStatus.ONGOING -> {
            if (requestUi.offer.isAccepted) {
                ButtonConfig(
                    text = stringResource(R.string.chat),
                    icon = ImageVector.vectorResource(R.drawable.ic_dialog_bold)
                )
            } else {
                ButtonConfig(
                    text = stringResource(R.string.view_offers),
                    icon = null
                )
            }
        }

        RequestStatus.COMPLETED -> {
            if (requestUi.craftsMan.isRated) {
                ButtonConfig()
            }
            else {
                ButtonConfig(
                    text = stringResource(R.string.rate),
                    icon = ImageVector.vectorResource(R.drawable.ic_star_outline)
                )
            }
        }

        RequestStatus.CANCELLED -> {
            ButtonConfig()
        }
    }
}