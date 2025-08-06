package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import androidx.compose.runtime.Composable
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyJobsScreen(
    viewModel: MyRequestCraftsmanViewModel = koinViewModel()
) {
    MyRequestScreenContent()
}
@Composable
private fun MyRequestScreenContent() {}


@PreviewMultiDevices
@Composable
fun MyRequestScreenPreview() {
    BasePreview {
        MyRequestScreenContent()
    }
}