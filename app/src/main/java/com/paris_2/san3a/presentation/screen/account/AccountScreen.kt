package com.paris_2.san3a.presentation.screen.account

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.account.components.AccountProgressIndicator
import com.paris_2.san3a.presentation.screen.account.components.AccountTypeContent
import com.paris_2.san3a.presentation.screen.account.components.LocationContent
import com.paris_2.san3a.presentation.screen.account.components.ProfileContent
import com.paris_2.san3a.presentation.screen.account.components.ServicesContent
import com.paris_2.san3a.presentation.screen.account.components.ShowYourWorkContent
import com.paris_2.san3a.presentation.screen.account.components.VerifyIdentityContent
import com.paris_2.san3a.presentation.shared.components.AppBackButton
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.asString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountScreen(viewModel: AccountViewModel = koinViewModel()) {
    val progress = viewModel.progress
    val title = viewModel.getTitle()
    val description = viewModel.getDescription()
    val textButton = viewModel.getButtonText()
    val currentScreen by viewModel.currentScreen
    val uiState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    val profileImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onCustomerProfilePhotoSelected(it)
        }
    }

    val frontNationalIdPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onFrontNationalIdSelected(it)
        }
    }

    val backNationalIdPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onBackNationalIdSelected(it)
        }
    }

    val workImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri>? ->
        uris?.forEach { uri ->
            context.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onWorkImageSelected(uris)
        }
    }

    AccountScreenContent(
        title = title.asString(),
        description = description.asString(),
        textButton = textButton.asString(),
        currentScreen = currentScreen,
        progress = progress,
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
            .safeContentPadding()
            .padding( top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (currentScreen != 0) {
                AppBackButton(onClickBackButton = interactionListener::onPreviousClicked)
            } else {
                Box(modifier = Modifier.size(48.dp))
            }
            AccountProgressIndicator(progress = progress, modifier= Modifier.padding(end = 48.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.display.xLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description,
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.body.large.regular,
        )
        when (currentScreen) {
            0 -> AccountTypeContent(
                modifier = Modifier.padding(vertical = 32.dp),
                onUserTypeSelected = interactionListener::onUserTypeSelected,
                selectedType = uiState.accountUiState.userType
            )

            1 -> ServicesContent(
                services = uiState.accountUiState.serviceUiState,
                onChipClick = interactionListener::onToggleServiceClicked,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            2 -> ProfileContent(
                modifier = Modifier.padding(top = 32.dp, bottom = 12.dp),
                name = uiState.accountUiState.customerName,
                onNameChanged = interactionListener::onCustomerNameChanged,
                onAddPhotoClick = onCustomerProfilePhotoClick,
                profilePhotoUri = uiState.accountUiState.customerProfilePhotoUri
            )

            3 -> when (uiState.accountUiState.userType) {
                UserType.CUSTOMER -> LocationContent(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onGetLocationClicked = interactionListener::onGovernmentBottomSheetVisibilityToggled,
                    isGovernmentSheetShowed = uiState.accountUiState.isGovernmentBottomSheetShowed,
                    onGovernmentDismissRequest = interactionListener::onGovernmentBottomSheetDismissed,
                    governments = uiState.accountUiState.governments,
                    onGovernmentSelected = interactionListener::onGovernmentSelected,
                    isCitiesSheetShowed = uiState.accountUiState.isCitiesBottomSheetShowed,
                    onCitiesDismissRequest = interactionListener::onCitiesBottomSheetDismissed,
                    onCitiesSelected = interactionListener::onCitiesSelected,
                    cities = uiState.accountUiState.cities,
                    government = uiState.accountUiState.locationUiState.government,
                    city = uiState.accountUiState.locationUiState.city,
                    addressInDetails = uiState.accountUiState.locationUiState.addressInDetails,
                    onAddressDetailsChange = interactionListener::onAddressDetailsChanged
                )

                UserType.CRAFTSMAN -> ShowYourWorkContent(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onAddWorkImagesClick = onWorkImageClick,
                    workImages = uiState.accountUiState.workImagesUris,
                    workDescription = uiState.accountUiState.workDescription,
                    onDescriptionChanged = interactionListener::onDescriptionChanged
                )

                else -> {}
            }

            4 -> when (uiState.accountUiState.userType) {
                UserType.CRAFTSMAN -> VerifyIdentityContent(

                    modifier = Modifier.padding(
                        top = 32.dp, bottom = 12.dp
                    ),
                    onFrontNationalIdClick,
                    onBackNationalIdClick,
                    uiState.accountUiState.frontOfNationalIdUri,
                    uiState.accountUiState.backOfNationalIdUri,
                    interactionListener::onNextClicked
                )

                else -> {}
            }
        }
        AppButton(
            onClick = interactionListener::onNextClicked,
            type = AppButtonType.Primary,
            text = textButton,
            state = AppButtonState.Enable,
            modifier = Modifier.fillMaxWidth()
        )
    }
}