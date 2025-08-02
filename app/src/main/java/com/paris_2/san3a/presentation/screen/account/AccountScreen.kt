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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.account.components.AccountProgressIndicator
import com.paris_2.san3a.presentation.screen.account.components.StepFourCraftsmanContent
import com.paris_2.san3a.presentation.screen.account.components.StepFourCustomerContent
import com.paris_2.san3a.presentation.screen.account.components.StepOneContent
import com.paris_2.san3a.presentation.screen.account.components.StepThreeCraftsmanContent
import com.paris_2.san3a.presentation.screen.account.components.StepThreeCustomerContent
import com.paris_2.san3a.presentation.screen.account.components.StepTwoContent
import com.paris_2.san3a.presentation.shared.components.AppBackButton
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
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

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onCustomerProfilePhotoSelected(it)
        }
    }

    AccountScreenContent(
        title = title,
        description = description,
        textButton = textButton,
        currentScreen = currentScreen,
        progress = progress,
        onPrevious = viewModel::previousStep,
        onNext = viewModel::nextStep,
        onUserTypeSelected = viewModel::updateUserType,
        onChipClick = viewModel::toggleServiceSelection,
        uiState = uiState,
        onCustomerNameChanged = viewModel::onCustomerNameChanged,
        onCustomerProfilePhotoClick = { imagePickerLauncher.launch(arrayOf("image/*")) }
    )
}

@Composable
fun AccountScreenContent(
    title: String,
    description: String,
    textButton: String,
    currentScreen: Int,
    progress: Float,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onUserTypeSelected: (UserType) -> Unit,
    onChipClick: (String) -> Unit,
    uiState: AccountScreenUiState,
    onCustomerNameChanged: (String) -> Unit,
    onCustomerProfilePhotoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (currentScreen != 0) {
                AppBackButton(onClickBackButton = onPrevious)
            } else {
                Box(modifier = Modifier.size(48.dp))
            }
            AccountProgressIndicator(progress = progress)
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
            0 -> StepOneContent(
                modifier = Modifier.padding(vertical = 32.dp),
                onUserTypeSelected = onUserTypeSelected,
                selectedType = uiState.accountUiState.userType
            )

            1 -> StepTwoContent(
                services = uiState.accountUiState.serviceUiState,
                onChipClick = onChipClick,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            2 -> when (uiState.accountUiState.userType) {
                UserType.CUSTOMER -> StepThreeCustomerContent(modifier = Modifier.padding(vertical = 32.dp))
                UserType.CRAFTSMAN -> StepThreeCraftsmanContent(modifier = Modifier.padding(vertical = 32.dp))
                else -> {}
            }

            3 -> when (uiState.accountUiState.userType) {
                UserType.CUSTOMER -> StepFourCustomerContent(
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 12.dp
                    ),
                    name = uiState.accountUiState.customerName,
                    onNameChanged = onCustomerNameChanged,
                    onAddPhotoClick = onCustomerProfilePhotoClick,
                    profilePhotoUri = uiState.accountUiState.customerProfilePhotoUri
                )
                UserType.CRAFTSMAN -> StepFourCraftsmanContent(
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 12.dp
                    )
                )

                else -> {}
            }
        }

        AppButton(
            onClick = onNext,
            type = AppButtonType.Primary,
            text = textButton,
            state = AppButtonState.Enable,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun AccountScreenPreview() {
    San3aTheme {
        AccountScreenContent(
            title = "What do you usually need help with?",
            description = "This helps us personalize your experience. You can change it anytime.",
            textButton = "Next",
            progress = 0.25f,
            currentScreen = 3,
            onNext = {},
            onPrevious = {},
            onUserTypeSelected = {},
            uiState = AccountScreenUiState(
                accountUiState = AccountUiState(
                    serviceUiState = listOf(
                        ServiceUiState(id = "1", serviceTitle = "Plumbing", isSelected = false),
                        ServiceUiState(id = "2", serviceTitle = "Electrical", isSelected = true),
                        ServiceUiState(id = "3", serviceTitle = "Cleaning", isSelected = false),
                        ServiceUiState(id = "1", serviceTitle = "AC Repair", isSelected = false),
                        ServiceUiState(id = "2", serviceTitle = "Furniture", isSelected = true),
                        ServiceUiState(id = "3", serviceTitle = "Landscaping", isSelected = false),
                        ServiceUiState(id = "1", serviceTitle = "Roofing", isSelected = false),
                        ServiceUiState(id = "2", serviceTitle = "Pest Control", isSelected = true),
                        ServiceUiState(id = "3", serviceTitle = "Carpentry", isSelected = false),
                        ServiceUiState(
                            id = "3",
                            serviceTitle = "Appliance Repair",
                            isSelected = false
                        ),
                        ServiceUiState(id = "3", serviceTitle = "Painting", isSelected = false),
                        ServiceUiState(id = "3", serviceTitle = "Masonry", isSelected = false),
                        ServiceUiState(
                            id = "3",
                            serviceTitle = "HVAC Maintenance",
                            isSelected = false
                        ),
                        ServiceUiState(
                            id = "3",
                            serviceTitle = "Pool Maintenance",
                            isSelected = false
                        ),

                        )
                )
            ),
            onChipClick = {},
            onCustomerNameChanged = {},
            onCustomerProfilePhotoClick = {}
        )
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    San3aTheme {
        AccountScreen()
    }

}