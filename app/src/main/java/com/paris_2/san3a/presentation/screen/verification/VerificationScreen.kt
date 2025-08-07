package com.paris_2.san3a.presentation.screen.verification

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.verification.components.VerifyIDContent
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.SnackBar
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun VerificationScreen(verificationViewModel: VerificationViewModel = koinViewModel()) {
    val uiState = verificationViewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val frontNationalIdPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            verificationViewModel.onFrontNationalIdSelected(it)
        }
    }

    val backNationalIdPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            verificationViewModel.onBackNationalIdSelected(it)
        }
    }

    VerificationScreenContent(
        verificationScreenState = uiState.value,
        verificationInteractionListener = verificationViewModel,
        onFrontOfNationalIdUploadClick = { frontNationalIdPickerLauncher.launch(arrayOf("image/*")) },
        onBackOfNationalIdUploadClick = { backNationalIdPickerLauncher.launch(arrayOf("image/*")) },
    )
}

@Composable
fun VerificationScreenContent(
    verificationScreenState: VerificationScreenState,
    verificationInteractionListener: VerificationInteractionListener,
    onFrontOfNationalIdUploadClick: () -> Unit,
    onBackOfNationalIdUploadClick: () -> Unit

) {

    val scroll = rememberScrollState()


    AppScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        topBar = {
            AppBar(
                title = stringResource(R.string.verification),
                modifier = Modifier.fillMaxWidth()
                    .background(Theme.colors.background.card),
                onBackClick = verificationInteractionListener::onBackClick
            )

        },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .statusBarsPadding()
            ) {
                when {
                    verificationScreenState.isNoInternet -> {
                        LostConnectionScreen(
                            onRetry = verificationInteractionListener::onClickRetry,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 60.dp)
                        )
                    }

                    verificationScreenState.isLoading -> {
                        LoadingScreen(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scroll)

                        ) {
                            VerifyIDContent(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                onFrontOfNationalIdUploadClick = { onFrontOfNationalIdUploadClick() },
                                onBackOfNationalIdUploadClick = { onBackOfNationalIdUploadClick() },
                                frontOfNationalIdUri = verificationScreenState.verificationUiState.frontOfNationalIdUri,
                                backOfNationalIdUri = verificationScreenState.verificationUiState.backOfNationalIdUri
                            )

                            val text = stringResource(R.string.save)
                            val buttonModifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp)


                            val typeButton = AppButtonType.Primary

                            AnimatedContent(
                                targetState = verificationScreenState.verificationUiState.frontOfNationalIdUri != null
                                        && verificationScreenState.verificationUiState.backOfNationalIdUri != null,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(Modifier.fillMaxSize()) {
                                    Spacer(Modifier.weight(1f))
                                    if(it){
                                        AppButton(
                                            text = text,
                                            onClick = verificationInteractionListener::onClickSave,
                                            modifier = buttonModifier,
                                            state = AppButtonState.Enable,
                                            type = typeButton,
                                        )
                                    } else{
                                        AppButton(
                                            text = text,
                                            onClick = {  },
                                            modifier = buttonModifier,
                                            state = AppButtonState.Disabled,
                                            type = typeButton,
                                        )
                                    }

                                }

                            }


                        }
                    }
                }

                AnimatedVisibility(verificationScreenState.showSnackBarError) {
                    verificationScreenState.errorMessage?.let {
                        SnackBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                                .align(Alignment.TopCenter),
                            text = verificationScreenState.errorMessage,
                            onClick = verificationInteractionListener::onDismissSnackBar

                        )
                    }
                }

                AnimatedVisibility(verificationScreenState.showSnackBarSuccess) {
                    verificationScreenState.successMessageSnackBar?.let {
                        SnackBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                                .align(Alignment.TopCenter),

                            text = verificationScreenState.successMessageSnackBar,
                            onClick = verificationInteractionListener::onDismissSnackBar

                        )
                    }
                }
            }
        }
    )



}

@Preview
@Composable
private fun VerificationScreenContentPreview() {
    VerificationScreenContent(
        verificationScreenState = VerificationScreenState(),
        verificationInteractionListener = object : VerificationInteractionListener {
            override fun onBackClick() {}
            override fun onClickSave() {
            }

            override fun onClickRetry() {

            }

            override fun onDismissSnackBar() {

            }
        },
        onFrontOfNationalIdUploadClick = TODO(),
        onBackOfNationalIdUploadClick = TODO()
    )

}