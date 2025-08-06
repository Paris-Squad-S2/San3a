package com.paris_2.san3a.presentation.screen.home.customer

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.screen.account.components.LocationContent
import com.paris_2.san3a.presentation.screen.home.craftsman.components.RequestBottomSheetContent
import com.paris_2.san3a.presentation.screen.home.customer.component.MostRequestedServices
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.screen.home.utils.getSuggestions
import com.paris_2.san3a.presentation.shared.components.AdCard
import com.paris_2.san3a.presentation.shared.components.AddPhotos
import com.paris_2.san3a.presentation.shared.components.AddPhotosContent
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.components.CategoryItem
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.components.RequestDescriptionContent
import com.paris_2.san3a.presentation.shared.components.RequestTitleContent
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
    action: CustomerHomeInteractionListener
) {
    val isArabic = remember { Locale.getDefault().language == "ar" }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            val newImages = uriList.map { it.toString() }
            action.addBottomSheetImages(newImages)
        }
    )

    val voiceSearchPrompt = stringResource(R.string.voice_search)

    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val spokenText = result.data
                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?.firstOrNull()
                spokenText?.let { action.onSpeechRecognized(it) }
            }
        }
    )

    LaunchedEffect(Unit) {
        val viewModel = action as? CustomerHomeViewModel
        if (viewModel == null) {
            return@LaunchedEffect
        }
        viewModel.triggerVoiceSearch.collect {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    voiceSearchPrompt
                )
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )
            }
            voiceLauncher.launch(intent)
        }
    }

    val servicesToDisplay = if (state.customerUiState.searchQuery.isNotEmpty())
        state.customerUiState.searchResults
    else
        state.customerUiState.services

    if (state.bottomSheetUiState.bottomSheetState) {
        BottomSheet(
            isVisible = true,
            onDismissRequest = { action.onDismissBottomSheet() }
        ) {
            when (state.bottomSheetUiState.bottomSheetStep) {
                BottomSheetStep.SELECT_SERVICE -> {
                    RequestBottomSheetContent(
                        title = state.bottomSheetUiState.bottomSheetServiceTitle,
                        icon = state.bottomSheetUiState.bottomSheetIconRes,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = stringResource(R.string.what_do_you_need_help_with),
                        buttonTitle = stringResource(R.string.next),
                        buttonIsActive = state.bottomSheetUiState.bottomSheetSubtitle.isNotEmpty(),
                        step = 1,
                        onButtonClick = { action.nextBottomSheetStep() },
                        onExitClick = { action.onDismissBottomSheet() },
                    ) {
                        RequestTitleContent(
                            value = state.bottomSheetUiState.bottomSheetSubtitle,
                            onValueChange = { action.setBottomSheetServiceSubTitle(it) },
                            suggestions = getSuggestions(serviceType = state.bottomSheetUiState.bottomSheetServiceTitle),
                            selectedSuggestion = state.bottomSheetUiState.bottomSheetSelectedSuggestion,
                            onChipClick = {
                                action.setBottomSheetSelectedSuggestion(it)
                                action.setBottomSheetServiceSubTitle(it)
                            },
                            modifier = Modifier,
                            hint = stringResource(R.string.select_a_service)
                        )
                    }
                }

                BottomSheetStep.PROBLEM_DESCRIPTION -> {
                    RequestBottomSheetContent(
                        title = state.bottomSheetUiState.bottomSheetServiceTitle,
                        icon = state.bottomSheetUiState.bottomSheetIconRes,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = stringResource(R.string.describe_the_problem_in_detail),
                        buttonIsActive = state.bottomSheetUiState.bottomSheetDescription.isNotEmpty(),
                        onButtonClick = { action.nextBottomSheetStep() },
                        buttonTitle = stringResource(R.string.next),
                        step = 2,
                        onClickBack = { action.previousBottomSheetStep() },
                        onExitClick = { action.onDismissBottomSheet() }
                    ) {
                        RequestDescriptionContent(
                            value = state.bottomSheetUiState.bottomSheetDescription,
                            onValueChange = { action.setBottomSheetDescription(it) },
                            hint = stringResource(R.string.describe_your_problem)
                        )
                    }
                }

                BottomSheetStep.SELECT_LOCATION -> {
                    RequestBottomSheetContent(
                        title = state.bottomSheetUiState.bottomSheetServiceTitle,
                        icon = state.bottomSheetUiState.bottomSheetIconRes,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = stringResource(R.string.where_are_you_from),
                        buttonTitle = stringResource(R.string.next),
                        buttonIsActive = state.bottomSheetUiState.bottomSheetAddressDetails.isNotEmpty(),
                        step = 3,
                        onButtonClick = { action.nextBottomSheetStep() },
                        onClickBack = { action.previousBottomSheetStep() },
                        onExitClick = { action.onDismissBottomSheet() }
                    ) {
                        LocationContent(
                            governments = state.bottomSheetUiState.bottomSheetGovernments,
                            cities = state.bottomSheetUiState.bottomSheetCities,
                            addressInDetails = state.bottomSheetUiState.bottomSheetAddressDetails,
                            onAddressDetailsChange = { action.setBottomSheetAddressDetails(it) },
                            isGovernmentSheetShowed = state.bottomSheetUiState.isGovernmentSheetVisible,
                            isCitiesSheetShowed = state.bottomSheetUiState.isCitySheetVisible,
                            onGovernmentDismissRequest = { action.showGovernmentSheet(false) },
                            onCitiesDismissRequest = { action.showCitySheet(false) },
                            onGovernmentSelected = {
                                action.setBottomSheetSelectedGovernment(it)
                            },
                            onCitiesSelected = {
                                action.setBottomSheetSelectedCity(it)
                            },
                            government = state.bottomSheetUiState.bottomSheetSelectedGovernment,
                            city = state.bottomSheetUiState.bottomSheetSelectedCity,
                            onGetLocationClicked = {
                                action.showGovernmentSheet(true)
                            },
                            locationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT
                        )
                    }
                }

                BottomSheetStep.IMAGE_UPLOAD -> {
                    RequestBottomSheetContent(
                        title = state.bottomSheetUiState.bottomSheetServiceTitle,
                        icon = state.bottomSheetUiState.bottomSheetIconRes,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = stringResource(R.string.add_some_photos),
                        optionalText = stringResource(R.string.optional),
                        buttonTitle = stringResource(R.string.create_request),
                        buttonIsActive = true,
                        step = 4,
                        onButtonClick = {
                            action.createRequest(
                                RequestServiceUiState(
                                    serviceType = state.bottomSheetUiState.bottomSheetServiceTitle,
                                    title = state.bottomSheetUiState.bottomSheetServiceTitle,
                                    description = state.bottomSheetUiState.bottomSheetDescription,
                                    location = "${state.bottomSheetUiState.bottomSheetSelectedGovernment}, ${state.bottomSheetUiState.bottomSheetSelectedCity}",
                                    locationDetails = state.bottomSheetUiState.bottomSheetAddressDetails,
                                    image = state.bottomSheetUiState.bottomSheetImages,
                                    userId = state.customerUiState.id
                                ),
                                state.bottomSheetUiState.bottomSheetServiceId
                            )
                        },
                        onClickBack = { action.previousBottomSheetStep() },
                        onExitClick = { action.onDismissBottomSheet() }
                    ) {
                        if (state.bottomSheetUiState.bottomSheetImages.isEmpty()) {
                            AddPhotosContent(
                                icon = painterResource(id = R.drawable.ic_camera_outline),
                                onClick = {
                                    imagePicker.launch("image/*")
                                }
                            )
                        } else {
                            AddPhotos(
                                photos = state.bottomSheetUiState.bottomSheetImages,
                                onClickAdd = {
                                    imagePicker.launch("image/*")
                                },
                                onClickDelete = { index ->
                                    action.deleteBottomSheetImageAt(index)
                                }
                            )
                        }
                    }
                }
            }
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
            actionIcon = {
                Icon(
                    modifier = Modifier
                        .clickable(onClick = {
                            action.onNotificationClick()
                        }),
                    painter = painterResource(R.drawable.ic_notification_outline),
                    contentDescription = null,
                    tint = Theme.colors.shade.primary
                )
            },
            leadingIcon = {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.good_morning,
                            state.customerUiState.currentUserName
                        ),

                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_outline),
                            contentDescription = "",
                            tint = Theme.colors.shade.secondary,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = "${state.customerUiState.government}, ${state.customerUiState.city}",
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.secondary
                        )
                    }
                }
            },
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                SearchBar(
                    value = state.customerUiState.searchQuery,
                    onValueChange = { action.onSearch(it) },
                    hint = stringResource(R.string.search),
                    onMicClick = {
                        action.onMicClick()
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                )
            }

            if (state.customerUiState.mostRequestedServices.isNotEmpty() && state.customerUiState.searchQuery.isEmpty()) {
                item {
                    MostRequestedServices(
                        services = state.customerUiState.mostRequestedServices,
                        isArabic = isArabic,
                        action = action
                    ) { selectedTitle, selectedServiceId ->
                        val iconRes = getResource(selectedServiceId)
                        action.initBottomSheet(selectedTitle, selectedServiceId, iconRes)
                    }
                }
            }


            if (state.customerUiState.searchQuery.isNotEmpty() && servicesToDisplay.isEmpty()) {
                item {
                    PlaceHolderScreen(
                        image = R.drawable.img_no_search_result,
                        title = R.string.no_results_found,
                        description = R.string.try_a_different_keyword_or_check_your_spelling_some_services_may_be_listed_under_other_names,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 24.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.find_what_you_need),
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 16.dp)
                    )
                }
                items(servicesToDisplay) { service ->
                    CategoryItem(
                        title = service.title[if (isArabic) ARABIC_NAME else ENGLISH_NAME] ?: "",
                        description = service.description[if (isArabic) ARABIC_DESCRIPTION else ENGLISH_DESCRIPTION]
                            ?: "",
                        tint = getResourceTint(service.id),
                        iconColor = getResourceColors(service.id),
                        isLarge = false,
                        painter = painterResource(getResource(service.id)),
                        modifier = Modifier
                            .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                        onclick = {
                            action.onServiceClick(service.id)
                        }
                    )
                }
            }
            if (state.customerUiState.searchQuery.isEmpty()) {
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

}

const val ARABIC_NAME = "arabicName"
const val ENGLISH_NAME = "englishName"
const val ARABIC_DESCRIPTION = "arabicDescription"
const val ENGLISH_DESCRIPTION = "englishDescription"
