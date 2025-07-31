package com.paris_2.san3a.presentation.screen.account

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paris_2.san3a.presentation.screen.account.components.AccountProgressIndicator
import com.paris_2.san3a.presentation.screen.account.components.StepFourContent
import com.paris_2.san3a.presentation.screen.account.components.StepOneContent
import com.paris_2.san3a.presentation.screen.account.components.StepThreeContent
import com.paris_2.san3a.presentation.screen.account.components.StepTwoContent
import com.paris_2.san3a.presentation.shared.components.AppBackButton
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AccountScreen(viewModel: AccountViewModel = viewModel()) {
    val progress by viewModel.progress
    val title = viewModel.getTitle()
    val description = viewModel.getDescription()
    val textButton = viewModel.getButtonText()
    val currentScreen by viewModel.currentScreen

    AccountScreenContent(
        title = title,
        description = description,
        textButton = textButton,
        currentScreen = currentScreen,
        progress = progress,
        onPrevious = viewModel::previousStep,
        onNext = viewModel::nextStep
    )
}
@Composable
fun AccountScreenContent(
    title : String,
    description : String,
    textButton : String,
    currentScreen: Int,
    progress: Float,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .padding(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            if (currentScreen != 0) {
                AppBackButton(onClickBackButton = onPrevious)
            } else {
                Box(modifier = Modifier.size(48.dp))
            }
            AccountProgressIndicator(progress = progress)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text =title,
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.display.xLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text =description,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.large.regular,
        )
        when (currentScreen) {
            0 -> StepOneContent( modifier = Modifier.padding(vertical = 32.dp))
            1 -> StepTwoContent( modifier = Modifier.padding(vertical = 32.dp))
            2 -> StepThreeContent( modifier = Modifier.padding(vertical =32.dp))
            3 -> StepFourContent( modifier = Modifier.padding(vertical = 32.dp))
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
            title ="What do you usually need help with?" ,
            description = "This helps us personalize your experience. You can change it anytime.",
            textButton = "Next",
            progress = 0.25f,
            currentScreen = 1,
            onNext = {},
            onPrevious = {},
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