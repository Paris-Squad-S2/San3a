package com.paris_2.san3a.presentation.screen.home.customer.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.home.craftsman.components.RequestBottomSheetContent
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
    onCreateRequestClick: () -> Unit = {},
    isVisible: Boolean,
    onExitClick: () -> Unit = {}
){
    var currentStep by remember { mutableStateOf(BottomSheetStep.SELECT_SERVICE) }
    var serviceTextValue by remember { mutableStateOf("") }
    var descriptionTextValue by remember { mutableStateOf("") }
    var locationTextValue by remember { mutableStateOf("") }
    var imageValue by remember { mutableStateOf(listOf<Int>()) }

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
                    onButtonClick = {onCreateRequestClick()},
                    onClickBack = {currentStep = BottomSheetStep.SELECT_SERVICE},
                    onExitClick = {
                        onExitClick()
                    }
                ) {
                    AddPhotosContent(
                        icon = painterResource(id = R.drawable.ic_camera_outline),
                        onClick = {}
                    )

                }

            }
        }
    }
}