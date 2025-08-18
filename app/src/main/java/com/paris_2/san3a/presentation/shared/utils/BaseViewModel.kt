package com.paris_2.san3a.presentation.shared.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.google.api.QuotaLimit
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Navigator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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
        onError: (Throwable) -> Unit,
        scope: CoroutineScope = viewModelScope,
        execute: suspend (CoroutineScope) -> T,
        onRetry: (suspend () -> Unit) ?= {},
        retryLimit: Int?=null
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
        return scope.launch(exceptionHandler) {
            try {
                val result = execute(scope)
                onSuccess?.invoke(result)
            } catch (e: Exception) {
                Log.e("BaseViewModel", "tryToExecute: Error executing operation", e)
                onError(e)
            }finally {
                retryLimit?.let {
                    for (i in 0..it)
                        onRetry?.invoke()
                }
            }
        }
    }

    protected fun <T> tryToObserve(
        observe: () -> Flow<T>,
        onEach: suspend (T?) -> Unit,
        onError: (Throwable) -> Unit = {},
        onStart: () -> Unit = {},
        scope: CoroutineScope = viewModelScope,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("BaseViewModel", "tryToObserve: Error in flow collection", throwable)
            onError(throwable)
        }

        return scope.launch(exceptionHandler) {
            observe()
                .onStart { onStart() }
                .catch {
                    Log.e("BaseViewModel", "tryToObserve: Error executing operation", it)
                    onError(it)
                }
                .also { onEach(null) }
                .collect { onEach(it) }
        }
    }

}