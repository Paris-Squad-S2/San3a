package com.paris_2.san3a.presentation.screen.messagesDetails

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.messagesDetails.components.MessageTextField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessageDetails(
    viewModel: MessagesDetailsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val imagePickerLauncher  = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
       viewModel.saveImagesUris(uris)
    }

    Box (
        modifier = Modifier.fillMaxSize().background(Yellow),
    ) {

        MessageTextField(
            value = state.value.message,
            onValueChange = viewModel::onMessageChange,
            imageIcon = painterResource(R.drawable.ic_image),
            voiceIcon =  painterResource(R.drawable.ic_voice),
            sendIcon =  painterResource(R.drawable.ic_send),
            onImageClick = {
                imagePickerLauncher.launch(MessagesDetailsViewModel.IMAGE_TYPE)
                // Todo (view model (send images))
            },
            onSendClick = viewModel::sendMessage,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}