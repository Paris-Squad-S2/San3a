package com.paris_2.san3a.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paris_2.san3a.presentation.navigation.Navigator
import com.paris_2.san3a.presentation.navigation.San3aNavGraph
import com.paris_2.san3a.presentation.screen.main.MainViewModel
import com.paris_2.san3a.presentation.shared.components.AppNavBarItem
import com.paris_2.san3a.presentation.shared.components.AppNavigationBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun San3aScaffold(
    navigator: Navigator = koinInject(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState = mainViewModel.screenState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedDestinationIndex by remember(currentBackStackEntry) {
        derivedStateOf {
            AppNavBarItem.destinations.indexOfFirst { item ->
                currentBackStackEntry?.destination?.hasRoute(item.destination::class) == true
            }.coerceAtLeast(0)
        }
    }

    val isVisible by remember {
        derivedStateOf {
            AppNavBarItem.destinations.any {
                currentBackStackEntry?.destination?.hasRoute(it.destination::class) == true
            }
        }
    }

    val scope = rememberCoroutineScope()


    San3aTheme(isDarkTheme = uiState.value.isDark) {
        AppScaffold(
            modifier = Modifier.fillMaxSize(),
            content = { San3aNavGraph(navController = navController) },
            bottomBar = {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    AppNavigationBar(
                        selectedItem = AppNavBarItem.destinations[selectedDestinationIndex],
                        onItemClick = { destination ->
                            scope.launch {
                                navigator.navigate(
                                    destination,
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(
                                            navigator.startGraph,
                                            inclusive = false
                                        )
                                        .build()
                                )
                            }
                        }
                    )

                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun San3aScaffoldPreview() {
    San3aScaffold()
}