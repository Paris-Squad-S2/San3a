package com.paris_2.san3a.presentation.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.account.components.AccountAppButton
import com.paris_2.san3a.presentation.screen.account.components.AccountProgressIndicator
import com.paris_2.san3a.presentation.screen.account.components.AccountTypeContent
import com.paris_2.san3a.presentation.screen.account.components.LocationContent
import com.paris_2.san3a.presentation.screen.account.components.ProfileContent
import com.paris_2.san3a.presentation.screen.account.components.ServicesContent
import com.paris_2.san3a.presentation.screen.account.components.ShowYourWorkContent
import com.paris_2.san3a.presentation.screen.account.components.VerifyIdentityContent
import com.paris_2.san3a.presentation.screen.account.components.rememberMultipleImagePickerLauncher
import com.paris_2.san3a.presentation.screen.account.components.rememberSingleImagePickerLauncher
import com.paris_2.san3a.presentation.shared.components.AppBackButton
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountScreen(viewModel: AccountViewModel = koinViewModel()) {
    val currentScreen by viewModel.currentScreen
    val uiState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    val profileImagePickerLauncher = rememberSingleImagePickerLauncher(
        context = context,
        onImageSelected = viewModel::onCustomerProfilePhotoSelected
    )

    val frontNationalIdPickerLauncher = rememberSingleImagePickerLauncher(
        context = context,
        onImageSelected = viewModel::onFrontNationalIdSelected
    )

    val backNationalIdPickerLauncher = rememberSingleImagePickerLauncher(
        context = context,
        onImageSelected = viewModel::onBackNationalIdSelected
    )

    val workImagePickerLauncher = rememberMultipleImagePickerLauncher(
        context = context,
        onImagesSelected = viewModel::onWorkImageSelected
    )

    AccountScreenContent(
        title = viewModel.getTitle().asString(),
        description = viewModel.getDescription().asString(),
        textButton = viewModel.getButtonText().asString(),
        currentScreen = currentScreen,
        progress = viewModel.progress,
        onCustomerProfilePhotoClick = { profileImagePickerLauncher.launch(arrayOf("image/*")) },
        onFrontNationalIdClick = { frontNationalIdPickerLauncher.launch(arrayOf("image/*")) },
        onBackNationalIdClick = { backNationalIdPickerLauncher.launch(arrayOf("image/*")) },
        onWorkImageClick = { workImagePickerLauncher.launch(arrayOf("image/*")) },
        interactionListener = viewModel,
        uiState = uiState
    )
}

@Composable
fun AccountScreenContent(
    title: String,
    description: String,
    textButton: String,
    currentScreen: Int,
    progress: Float,
    onCustomerProfilePhotoClick: () -> Unit,
    onFrontNationalIdClick: () -> Unit,
    onBackNationalIdClick: () -> Unit,
    onWorkImageClick: () -> Unit,
    interactionListener: AccountInteractionListener,
    uiState: AccountScreenUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .imePadding()
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (currentScreen != 0) {
                AppBackButton(onClickBackButton = interactionListener::onPreviousClicked)
            } else {
                Box(modifier = Modifier.size(48.dp))
            }
            AccountProgressIndicator(progress = progress, modifier = Modifier.padding(end = 48.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.display.xLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = description,
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.body.large.regular,
        )
        when (currentScreen) {
            0 -> {
                AccountTypeContent(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onUserTypeSelected = interactionListener::onUserTypeSelected,
                    selectedType = uiState.accountUiState.userType
                )
                AccountAppButton(
                    onClickButton = interactionListener::onUserTypeButtonClicked,
                    textButton = textButton,
                    state = uiState.accountUiState.accountButtonState.userTypeButtonState
                )
            }

            1 -> {
                ServicesContent(
                    services = uiState.accountUiState.serviceUiState,
                    onChipClick = interactionListener::onToggleServiceClicked,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                AccountAppButton(
                    onClickButton = interactionListener::onServiceButtonClicked,
                    textButton = textButton,
                    state = uiState.accountUiState.accountButtonState.serviceButtonState
                )
            }

            2 -> {
                ProfileContent(
                    modifier = Modifier.padding(top = 32.dp),
                    name = uiState.accountUiState.customerName,
                    onNameChanged = interactionListener::onCustomerNameChanged,
                    onAddPhotoClick = onCustomerProfilePhotoClick,
                    profilePhotoUri = uiState.accountUiState.customerProfilePhotoUri
                )
                AccountAppButton(
                    onClickButton = interactionListener::onProfileButtonClicked,
                    textButton = textButton,
                    state = uiState.accountUiState.accountButtonState.profileButtonState
                )
            }

            3 -> when (uiState.accountUiState.userType) {
                UserType.CUSTOMER -> {
                    LocationContent(
                        modifier = Modifier.padding(vertical = 32.dp),
                        onGetLocationClicked = interactionListener::onGovernmentBottomSheetVisibilityToggled,
                        isGovernmentSheetShowed = uiState.accountUiState.isGovernmentBottomSheetShowed,
                        onGovernmentDismissRequest = interactionListener::onGovernmentBottomSheetDismissed,
                        governments = uiState.accountUiState.governments,
                        onGovernmentSelected = interactionListener::onGovernmentSelected,
                        onCitiesSelected = interactionListener::onCitiesSelected,
                        cities = uiState.accountUiState.cities,
                        government = uiState.accountUiState.locationUiState.governorate?.name.orEmpty(),
                        city = uiState.accountUiState.locationUiState.city?.name.orEmpty(),
                        addressInDetails = uiState.accountUiState.locationUiState.addressInDetails,
                        onAddressDetailsChange = interactionListener::onAddressDetailsChanged,
                        locationBottomSheetContentType = uiState.accountUiState.locationType
                    )
                    AccountAppButton(
                        onClickButton = interactionListener::onLocationButtonClicked,
                        textButton = textButton,
                        state = uiState.accountUiState.accountButtonState.locationButtonState
                    )
                }
                UserType.CRAFTSMAN -> {
                    ShowYourWorkContent(
                        modifier = Modifier.padding(vertical = 32.dp),
                        onAddWorkImagesClick = onWorkImageClick,
                        workImages = uiState.accountUiState.workImagesUris,
                        workDescription = uiState.accountUiState.workDescription,
                        onDescriptionChanged = interactionListener::onDescriptionChanged,
                        onDeleteImage = interactionListener::onDeleteWorkImageClicked
                    )
                    AccountAppButton(
                        onClickButton = interactionListener::onShowWorkButtonClicked,
                        textButton = textButton,
                        state = uiState.accountUiState.accountButtonState.workShowCaseButtonState
                    )
                }
                else -> {}
            }

            4 -> when (uiState.accountUiState.userType) {
                UserType.CRAFTSMAN -> {
                    VerifyIdentityContent(
                        modifier = Modifier.padding(top = 32.dp, bottom = 12.dp),
                        onFrontOfNationalIdUploadClick = onFrontNationalIdClick,
                        onBackOfNationalIdUploadClick = onBackNationalIdClick,
                        frontOfNationalIdUri = uiState.accountUiState.frontOfNationalIdUri,
                        backOfNationalIdUri = uiState.accountUiState.backOfNationalIdUri,
                        onVerifyLaterClick = interactionListener::onVerifyIdentityButtonClicked
                    )
                    AccountAppButton(
                        onClickButton = interactionListener::onVerifyIdentityButtonClicked,
                        textButton = textButton,
                        state = uiState.accountUiState.accountButtonState.verifyIdentityButtonState
                    )
                }
                else -> {}
            }
        }
    }
}