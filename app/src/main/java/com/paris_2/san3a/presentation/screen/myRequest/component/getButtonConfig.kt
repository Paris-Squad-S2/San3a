package com.paris_2.san3a.presentation.screen.myRequest.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestCustomerUi

data class ButtonConfig(
    val text: String?,
    val icon: ImageVector?
)

@Composable
fun getButtonConfig(requestUi: MyRequestCustomerUi): ButtonConfig {
    return when (requestUi.status) {
        RequestStatus.ONGOING -> {
            if (requestUi.isAcceptedOffer) {
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
            if (requestUi.isRated) {
                ButtonConfig(
                    text = null,
                    icon = null
                )
            } else {
                ButtonConfig(
                    text = stringResource(R.string.rate),
                    icon = ImageVector.vectorResource(R.drawable.ic_clipboard_outline)
                )
            }
        }

        RequestStatus.CANCELLED -> {
            ButtonConfig(
                text = null,
                icon = null
            )
        }
    }
}