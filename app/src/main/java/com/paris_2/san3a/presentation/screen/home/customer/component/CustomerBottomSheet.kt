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
import com.paris_2.san3a.presentation.screen.home.craftsman.components.RequestBottomSheetContent
import com.paris_2.san3a.presentation.screen.home.customer.RequestServiceUiState
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
    onExitClick: () -> Unit = {},
    requestService: MutableState<RequestServiceUiState?>
){
    var currentStep by remember { mutableStateOf(BottomSheetStep.SELECT_SERVICE) }
    var serviceTextValue by remember { mutableStateOf("") }
    var descriptionTextValue by remember { mutableStateOf("") }
    var locationTextValue by remember { mutableStateOf("") }
    var locationDescriptionValue by remember { mutableStateOf("") }
    var imageValue by remember { mutableStateOf(listOf<String>()) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            val newImages = uriList.map { it.toString() }
            imageValue = imageValue + newImages
        }
    )
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
                    suggestions = listOf("Service 1", "Service 2", "Service 3"),
                    selectedSuggestion = null,
                    onChipClick = { serviceTextValue = it},
                    modifier = Modifier,
                    hint = "Select a service")
                }

            }

            BottomSheetStep.PROBLEM_DESCRIPTION -> {
                    RequestBottomSheetContent(
                        title = title,
                        icon = icon,
                        color = Theme.colors.additional.primary.blue,
                        subTitle = "Describe the problem in detail",
                        buttonIsActive = descriptionTextValue.isNotEmpty(),
                        onButtonClick = {currentStep = BottomSheetStep.IMAGE_UPLOAD},
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
                    buttonTitle = "Create Request",
                    buttonIsActive = locationTextValue.isNotEmpty(),
                    step = 3,
                    onButtonClick = {currentStep = BottomSheetStep.SELECT_SERVICE},
                    onClickBack = {currentStep = BottomSheetStep.SELECT_SERVICE},
                    onExitClick = {
                        onExitClick()
                    }
                ) {

                }

            }
            BottomSheetStep.IMAGE_UPLOAD -> {
                RequestBottomSheetContent(
                    title = title,
                    icon = icon,
                    color = Theme.colors.additional.primary.blue,
                    subTitle = "Describe the problem in detail",
                    buttonTitle = "Create Request",
                    buttonIsActive = imageValue.isNotEmpty(),
                    step = 4,
                    onButtonClick = {
                        requestService.value =
                            RequestServiceUiState(
                                serviceType = title,
                                title = serviceTextValue ,
                                description = descriptionTextValue,
                                location = locationTextValue,
                                locationDetails = locationDescriptionValue,
                                image = imageValue,
                            )
                    },
                    onClickBack = {currentStep = BottomSheetStep.SELECT_SERVICE},
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