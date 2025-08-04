package com.paris_2.san3a.presentation.screen.home.customer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.home.customer.component.CustomerBottomSheetService
import com.paris_2.san3a.presentation.screen.home.customer.component.MostRequestedServices
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.shared.components.AdCard
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.CategoryItem
import com.paris_2.san3a.presentation.shared.components.SearchBar
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale


@Composable
fun CustomerHomeScreen(
    viewModel: CustomerHomeViewModel = koinViewModel(),
) {
    val customerScreenState by viewModel.screenState.collectAsStateWithLifecycle()
    CustomerHomeScreenContent(
        state = customerScreenState,
        action = viewModel
        )
}

@Composable
private fun CustomerHomeScreenContent(
    state: CustomerHomeUiState,
    action : CustomerHomeInteractionListener
) {
    val isArabic = remember { Locale.getDefault().language == "ar" }
    var title by remember { mutableStateOf("") }
    var serviceId by remember { mutableStateOf("") }
    val requestService = remember { mutableStateOf<RequestServiceUiState?>(null) }

    if (state.bottomSheetState){
        CustomerBottomSheetService(
            title = title,
            icon = getResource(serviceId),
            isVisible = true,
            onExitClick = {
                action.onDismissBottomSheet()
            },
            requestService = requestService,
            userId = state.customerUiState.id
        )
        if(requestService.value != null){
            action.createRequest(requestService.value!! , serviceId)
            requestService.value = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        AppBar(
            modifier = Modifier
                .padding(top = 40.dp),
            onActionIconClick = { action.onNotificationClick() },
            title = stringResource(
                R.string.good_morning,
                state.customerUiState.currentUserName
            ),
            location = "${state.customerUiState.government}, ${state.customerUiState.city}",
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen),
        ) {
            item {
                SearchBar(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    hint = "Search...",
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                )
            }

            if (state.customerUiState.mostRequestedServices.isNotEmpty()) {
                item {
                    MostRequestedServices(
                        services = state.customerUiState.mostRequestedServices,
                        isArabic = isArabic,
                        action = action
                    ){ selectedTitle, selectedServiceId ->
                        title = selectedTitle
                        serviceId = selectedServiceId
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.find_what_you_need),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier
                        .padding(start= 16.dp,bottom = 16.dp)
                )
            }

            items(state.customerUiState.services) { service ->
                CategoryItem(
                    title = service.title[if (isArabic) ARABIC_NAME else ENGLISH_NAME] ?: "",
                    description = service.description[if (isArabic) ARABIC_DESCRIPTION else ENGLISH_DESCRIPTION]
                        ?: "",
                    tint = getResourceTint(service.id),
                    iconColor = getResourceColors(service.id),
                    isLarge = false,
                    painter = painterResource(getResource(service.id)),
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .clickable {
                            action.onServiceClick(service.id)
                            title =
                                service.title[if (isArabic) ARABIC_NAME else ENGLISH_NAME] ?: ""
                            serviceId = service.id
                        }
                )
            }

            item {
                AdCard(
                    title = stringResource(R.string.got_a_skill_start_earning),
                    caption = stringResource(R.string.create_your_craftsman_account_and_get_job_requests),
                    buttonTitle = stringResource(R.string.become_a_craftsman),
                    onClick = { action.onBecomeCraftsmanClick() },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }

        }

    }

}
const val ARABIC_NAME = "arabicName"
const val ENGLISH_NAME = "englishName"
const val ARABIC_DESCRIPTION = "arabicDescription"
const val ENGLISH_DESCRIPTION = "englishDescription"
