package com.paris_2.san3a.presentation.screen.message.details

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessageDetails(
    viewModel: MessagesDetailsViewModel = koinViewModel()
) {
    val imagePickerLauncher  = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
       viewModel.saveImagesUris(uris)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            modifier = Modifier
                .padding(top = 80.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                imagePickerLauncher.launch("image/*")
            }
        ) {
            Text("Add Images")
        }
    }
}