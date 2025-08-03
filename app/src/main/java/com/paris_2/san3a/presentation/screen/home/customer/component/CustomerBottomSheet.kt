package com.paris_2.san3a.presentation.screen.home.customer.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.account.components.LocationContent
import com.paris_2.san3a.presentation.screen.home.craftsman.components.RequestBottomSheetContent
import com.paris_2.san3a.presentation.screen.home.customer.RequestServiceUiState
import com.paris_2.san3a.presentation.screen.home.utils.getSuggestions
import com.paris_2.san3a.presentation.shared.components.AddPhotos
import com.paris_2.san3a.presentation.shared.components.AddPhotosContent
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.components.RequestDescriptionContent
import com.paris_2.san3a.presentation.shared.components.RequestTitleContent
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

enum class BottomSheetStep {
    SELECT_SERVICE,
    PROBLEM_DESCRIPTION,
    SELECT_LOCATION,
    IMAGE_UPLOAD
}
@Composable
fun CustomerBottomSheetService(
    title: String,
    icon: Int,
    isVisible: Boolean,
    userId: String,
    onExitClick: () -> Unit = {},
    requestService: MutableState<RequestServiceUiState?>
){
    var currentStep by remember { mutableStateOf(BottomSheetStep.SELECT_SERVICE) }
    var serviceTextValue by remember { mutableStateOf("") }
    var descriptionTextValue by remember { mutableStateOf("") }
    var imageValue by remember { mutableStateOf(listOf<String>()) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            val newImages = uriList.map { it.toString() }
            imageValue = imageValue + newImages
        }
    )
    var selectedSuggestion by remember { mutableStateOf<String?>(null) }
    var governments by remember { mutableStateOf(listOf("Cairo", "Giza", "Alex")) }
    var cities by remember { mutableStateOf(listOf("Nasr City", "Dokki", "Smouha")) }
    var selectedGovernment by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var addressDetails by remember { mutableStateOf("") }

    var isGovernmentSheetVisible by remember { mutableStateOf(false) }
    var isCitySheetVisible by remember { mutableStateOf(false) }

    BottomSheet(
        isVisible = isVisible
    ){
        when(currentStep){
            BottomSheetStep.SELECT_SERVICE -> {
                RequestBottomSheetContent(
                    title = title,
                    icon = icon,
                    color = Theme.colors.additional.primary.blue,
                    subTitle = "What do you need help with?",
                    buttonTitle = "Next",
                    buttonIsActive = serviceTextValue.isNotEmpty(),
                    step = 1,
                    onButtonClick = {currentStep = BottomSheetStep.PROBLEM_DESCRIPTION},
                    onExitClick = {
                        onExitClick()
                    },
                ) {
                    RequestTitleContent(
                        value = serviceTextValue,
                        onValueChange = { serviceTextValue = it },
                        suggestions = getSuggestions(serviceType = title),
                        selectedSuggestion = selectedSuggestion,
                        onChipClick = {
                            selectedSuggestion = it
                            serviceTextValue = it
                        },
                        modifier = Modifier,
                        hint = "Select a service"
                    )
                }

            }

            BottomSheetStep.PROBLEM_DESCRIPTION -> {
                    RequestBottomSheetContent(
                        title = title,
                        icon = icon,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = "Describe the problem in detail",
                        buttonIsActive = descriptionTextValue.isNotEmpty(),
                        onButtonClick = {currentStep = BottomSheetStep.SELECT_LOCATION},
                        buttonTitle = "Next",
                        step = 2,
                        onClickBack = {currentStep = BottomSheetStep.SELECT_SERVICE},
                        onExitClick = {
                            onExitClick()
                        }
                    ) {
                        RequestDescriptionContent(
                            value = descriptionTextValue,
                            onValueChange = { descriptionTextValue = it },
                            hint = "Describe your problem"
                        )

                    }
            }
            BottomSheetStep.SELECT_LOCATION -> {
                RequestBottomSheetContent(
                    title = title,
                    icon = icon,
                    color = Theme.colors.additional.primary.blue,
                    subTitle = "Describe the problem in detail",
                    buttonTitle = "Next",
                    buttonIsActive = addressDetails.isNotEmpty(),
                    step = 3,
                    onButtonClick = {currentStep = BottomSheetStep.IMAGE_UPLOAD},
                    onClickBack = {currentStep = BottomSheetStep.SELECT_SERVICE},
                    onExitClick = {
                        onExitClick()
                    }
                ) {
                    LocationContent(
                        governments = governments,
                        cities = cities,
                        addressInDetails = addressDetails,
                        onAddressDetailsChange = { addressDetails = it },
                        isGovernmentSheetShowed = isGovernmentSheetVisible,
                        isCitiesSheetShowed = isCitySheetVisible,
                        onGovernmentDismissRequest = { isGovernmentSheetVisible = false },
                        onCitiesDismissRequest = { isCitySheetVisible = false },
                        onGovernmentSelected = {
                            selectedGovernment = it
                            isGovernmentSheetVisible = false
                        },
                        onCitiesSelected = {
                            selectedCity = it
                            isCitySheetVisible = false
                        },
                        government = selectedGovernment,
                        city = selectedCity,
                        onGetLocationClicked = {
                            isGovernmentSheetVisible = true
                            isCitySheetVisible = true
                        }
                    )
                }

            }
            BottomSheetStep.IMAGE_UPLOAD -> {
                RequestBottomSheetContent(
                    title = title,
                    icon = icon,
                    color = Theme.colors.additional.primary.blue,
                    subTitle = "Describe the problem in detail",
                    buttonTitle = "Create Request",
                    buttonIsActive = true,
                    step = 4,
                    onButtonClick = {
                        requestService.value =
                            RequestServiceUiState(
                                serviceType = title,
                                title = serviceTextValue ,
                                description = descriptionTextValue,
                                location = "$selectedGovernment, $selectedCity",
                                locationDetails = addressDetails,
                                image = imageValue,
                                userId = userId
                            )
                    },
                    onClickBack = { currentStep = BottomSheetStep.SELECT_LOCATION },
                    onExitClick = {
                        onExitClick()
                    }
                ) {
                    if (imageValue.isEmpty()){
                        AddPhotosContent(
                            icon = painterResource(id = R.drawable.ic_camera_outline),
                            onClick = {
                                imagePicker.launch("image/*")
                            }
                        )
                    }else {
                        AddPhotos(
                            photos = imageValue,
                            onClickAdd = {
                                imagePicker.launch("image/*")
                            },
                            onClickDelete = { index ->
                                imageValue = imageValue.toMutableList().apply { removeAt(index) }
                            }
                        )
                    }
                }
            }
        }
    }
}