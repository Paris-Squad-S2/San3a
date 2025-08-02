package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.account.UserType
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme

@Composable
fun AccountTypeContent(
    selectedType: UserType?,
    onUserTypeSelected: (UserType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountSelectionCard(
            title = stringResource(R.string.customer),
            caption = stringResource(R.string.i_need_help_with_a_service),
            userImage = painterResource(R.drawable.customer),
            isSelect = selectedType == UserType.CUSTOMER,
            modifier = modifier.weight(1f),
            onClick = { onUserTypeSelected(UserType.CUSTOMER) }
        )
        AccountSelectionCard(
            title = stringResource(R.string.craftman),
            caption = stringResource(R.string.i_offer_services),
            userImage = painterResource(R.drawable.craftsman),
            isSelect = selectedType == UserType.CRAFTSMAN,
            modifier = modifier.weight(1f),
            onClick = { onUserTypeSelected(UserType.CRAFTSMAN) }
        )
    }
}
@Preview
@Composable
private fun StepOneContentPreview() {
    San3aTheme {
        AccountTypeContent(
            selectedType = UserType.CUSTOMER,
            onUserTypeSelected = {}
        )
    }
}
