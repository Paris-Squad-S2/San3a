package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class NavigatorImpl(override val startGraph: Graph) : Navigator {
    private val _navigateEvent = MutableSharedFlow<NavigationEvent>()
    override val navigationEvent = _navigateEvent.asSharedFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: Destination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 500) {
                lastNavigateTime = now
                _navigateEvent.emit(
                    NavigationEvent.Navigate(destination = destination, navOptions = navOptions)
                )
            }
        }
    }

    override suspend fun navigateUp() {
        _navigateEvent.emit(NavigationEvent.NavigateUp)
    }
}