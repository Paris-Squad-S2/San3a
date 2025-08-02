package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class NavigatorImpl(override val startGraph: Graph) : Navigator {
    private val _navigateEvent = Channel<NavigationEvent>()
    override val navigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: Destination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    NavigationEvent.Navigate(destination = destination, navOptions = navOptions)
                )
            }
        }
    }

    override suspend fun navigateUp() {
        _navigateEvent.send(NavigationEvent.NavigateUp)
    }
}