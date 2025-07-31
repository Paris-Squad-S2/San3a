package com.paris_2.san3a.presentation.shared.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Navigator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel<S>(initialState: S) : ViewModel(), KoinComponent {

    private val _screenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = _screenState.asStateFlow()

    private val navigator: Navigator by inject()

    protected fun navigate(destination: Destination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

    fun updateState(newState: S) {
        _screenState.update { newState }
    }

    protected fun <T> tryToExecute(
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (String) -> Unit,
        scope: CoroutineScope = viewModelScope,
        execute: suspend () -> T,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable.message ?: "Unexpected error")
        }
        return scope.launch(exceptionHandler) {
            try {
                val result = execute()
                onSuccess?.invoke(result)
            } catch (e: Exception) {
                onError(e.message ?: "Unexpected error")
            }
        }
    }
}