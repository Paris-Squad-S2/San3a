package com.paris_2.san3a.presentation.screen.requestDetails.customer

import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.RequestOfferUiState
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.RequestServiceUIState
import com.paris_2.san3a.presentation.shared.components.AppButtonState

data class CustomerRequestDetailsScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val uiState: CustomerRequestDetailsUiState = CustomerRequestDetailsUiState(),
)

data class CustomerRequestDetailsUiState(
    val request: RequestServiceUIState = RequestServiceUIState(),
    val offers: Map<String, RequestOfferUiState> = emptyMap(),
    val showDropMenu: Boolean = false,
    val showDeleteRequestBottomSheet: Boolean = false,
    val bottomSheetButtonState: AppButtonState = AppButtonState.Enable,
)