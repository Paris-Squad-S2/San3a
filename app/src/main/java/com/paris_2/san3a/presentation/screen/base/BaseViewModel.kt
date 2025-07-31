package com.paris_2.san3a.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel<T>(initState: T) : ViewModel(), KoinComponent {

    protected val _uiState = MutableStateFlow(initState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    private val navigator: Navigator by inject()

    protected fun navigate(destination: Destination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

    protected fun launchSafely(
        onLoading: (() -> Unit)? = null,
        onSuccess: suspend () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onLoading?.let { it() }
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unexpected error")
            }
        }
    }

}