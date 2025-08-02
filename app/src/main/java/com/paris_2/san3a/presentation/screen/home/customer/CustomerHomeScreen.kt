package com.paris_2.san3a.presentation.screen.home.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.paris_2.san3a.domain.entity.MostRequestedServices
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
    if (state.bottomSheetState){
        CustomerBottomSheetService(
            title = title,
            icon = getResource(serviceId),
            isVisible = true,
            onExitClick = {
                action.onDismissBottomSheet()
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        AppBar(
            onActionIconClick = { action.onNotificationClick() },
            title = stringResource(
                R.string.good_morning,
                state.customerUiState.currentUserName
            ),
            location = state.customerUiState.location,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen),
        ) {
            item {
                SearchBar(
                    value = " ",
                    onValueChange = { /*TODO*/ },
                    hint = "Search for service",
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                )
            }
            item {
                MostRequestedServices(
                    listOfServices = state.customerUiState.mostRequestedServices,
                    modifier = Modifier
                        .padding(bottom = 24.dp, start = 16.dp)
                )
            }
            item {
                Text(
                    text = "Find What You Need",
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier
                        .padding(start= 16.dp,bottom = 16.dp)
                )
            }

            items(state.customerUiState.services) { service ->
                CategoryItem(
                    title = service.title[if (isArabic) "arabicName" else "englishName"] ?: "",
                    description = service.description[if (isArabic) "arabicDescription" else "englishDescription"]
                        ?: "",
                    tint = getResourceTint(service.id),
                    iconColor = getResourceColors(service.id),
                    isLarge = false,
                    painter = painterResource(getResource(service.id)),
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
                        .clickable{
                            action.onServiceClick(service.id)
                            title = service.title[if (isArabic) "arabicName" else "englishName"] ?: ""
                            serviceId = service.id
                        }
                )
            }

            item {
                AdCard(
                    title = "Got a skill? Start earning",
                    caption = "Create your craftsman account and get job requests.",
                    buttonTitle = "Become a Craftsman",
                    onClick = { action.onBecomeCraftsmanClick() },
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
            }

        }

    }
}
@Preview
@Composable
fun PreviewHomeScreen() {
    CustomerHomeScreenContent(
        state = CustomerHomeUiState(
            customerUiState = CustomerUiState(
                currentUserName = "Mahmoud",
                location = "Cairo,Egypt",
                mostRequestedServices = listOf(
                    MostRequestedServices(
                        id = "1",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        requestedCount = 6
                    ),
                    MostRequestedServices(
                        id = "2",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        requestedCount = 2
                    ),
                    MostRequestedServices(
                        id = "3",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        requestedCount = 4
                    )
                ),
                services = listOf(
                    Service(
                        id = "1",
                        title = mapOf("en" to "Plumbing", "ar" to "سباكة"),
                        description = mapOf("en" to "Pipes, faucets, water heaters", "ar" to "الأنابيب، الصنابير، سخانات المياه")
                    ),
                    Service(
                        id = "2",
                        title = mapOf("en" to "Electrical Services", "ar" to "الخدمات الكهربائية"),
                        description = mapOf("en" to "Wiring, installations, repairs", "ar" to "الأسلاك والتركيبات والإصلاحات")
                    ),
                    Service(
                        id = "3",
                        title = mapOf("en" to "Cleaning", "ar" to "تنظيف"),
                        description = mapOf("en" to "House, office, deep cleaning", "ar" to "المنزل، المكتب، التنظيف العميق")
                    ),
                    Service(
                        id = "4",
                        title = mapOf("en" to "AC Repair", "ar" to "إصلاح مكيف الهواء"),
                        description = mapOf("en" to "Air conditioning, heating", "ar" to "تكييف الهواء والتدفئة")
                    ),
                    Service(
                        id = "5",
                        title = mapOf("en" to "Furniture", "ar" to "أثاث"),
                        description = mapOf("en" to "Furniture assembly and repair", "ar" to "تركيب وإصلاح الأثاث")
                    ),
                    Service(
                        id = "6",
                        title = mapOf("en" to "Landscaping", "ar" to "تنسيق الحدائق"),
                        description = mapOf("en" to "Design, maintenance, gardening", "ar" to "التصميم والصيانة والبستنة")
                    ),
                    Service(
                        id = "7",
                        title = mapOf("en" to "Roofing", "ar" to "أسقف"),
                        description = mapOf("en" to "Roof installation and repair", "ar" to "تركيب وإصلاح الأسقف")
                    ),
                    Service(
                        id = "8",
                        title = mapOf("en" to "Pest Control", "ar" to "مكافحة حشرات"),
                        description = mapOf("en" to "Pest elimination services", "ar" to "خدمات القضاء على الحشرات")
                    ),
                    Service(
                        id = "9",
                        title = mapOf("en" to "Carpentry", "ar" to "نجارة"),
                        description = mapOf("en" to "Woodwork and carpentry services", "ar" to "خدمات النجارة والأعمال الخشبية")
                    ),
                    Service(
                        id = "10",
                        title = mapOf("en" to "Appliance Repair", "ar" to "تصليح أجهزة"),
                        description = mapOf("en" to "Home appliance repair", "ar" to "تصليح الأجهزة المنزلية")
                    ),
                    Service(
                        id = "11",
                        title = mapOf("en" to "Painting", "ar" to "دهان"),
                        description = mapOf("en" to "Interior, exterior, touch-ups", "ar" to "الداخلية والخارجية واللمسات النهائية")
                    ),
                    Service(
                        id = "12",
                        title = mapOf("en" to "Masonry", "ar" to "بناء"),
                        description = mapOf("en" to "Brickwork and masonry services", "ar" to "خدمات البناء والطوب")
                    ),
                    Service(
                        id = "13",
                        title = mapOf("en" to "HVAC Maintenance", "ar" to "صيانة تكييف"),
                        description = mapOf("en" to "Heating and cooling system maintenance", "ar" to "صيانة أنظمة التدفئة والتبريد")
                    ),
                    Service(
                        id = "14",
                        title = mapOf("en" to "Pool Maintenance", "ar" to "صيانة مسابح"),
                        description = mapOf("en" to "Swimming pool cleaning and maintenance", "ar" to "تنظيف وصيانة المسابح")
                    )
                )
                )

        ),
        action = object : CustomerHomeInteractionListener {
            override fun onServiceClick(serviceId: String) {}
            override fun onNotificationClick() {}

            override fun onSearch(query: String) {}


            override fun onBecomeCraftsmanClick() {}
            override fun onDismissBottomSheet() {}
        }
    )
}