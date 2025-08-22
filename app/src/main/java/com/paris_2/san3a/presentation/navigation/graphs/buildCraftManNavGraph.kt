package com.paris_2.san3a.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.home.craftsman.CraftsmanHomeScreen
import com.paris_2.san3a.presentation.screen.messages.MessagesScreen
import com.paris_2.san3a.presentation.screen.messagesDetails.MessageDetailsScreen
import com.paris_2.san3a.presentation.screen.more.locationScreen.LocationScreen
import com.paris_2.san3a.presentation.screen.more.moreScreen.MoreScreen
import com.paris_2.san3a.presentation.screen.myService.MyServiceScreen
import com.paris_2.san3a.presentation.screen.notification.NotificationScreen
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.CraftsManRequestDetailsScreen
import com.paris_2.san3a.presentation.screen.requests.craftsman.MyJobsScreen
import com.paris_2.san3a.presentation.screen.verification.VerificationScreen

fun NavGraphBuilder.buildCraftManNavGraph() {
    navigation<Destinations.CraftManGraph>(startDestination = Destinations.Home) {
        composable<Destinations.Home> { CraftsmanHomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MessageDetails> { MessageDetailsScreen() }
        composable<Destinations.Requests> { MyJobsScreen() }
        composable<Destinations.RequestDetails> { CraftsManRequestDetailsScreen() }
        composable<Destinations.Notification> { NotificationScreen() }
        composable<Destinations.More> { MoreScreen() }
        composable<Destinations.Verification> { VerificationScreen() }
        composable<Destinations.MyService> { MyServiceScreen() }
        composable<Destinations.Location> { LocationScreen() }
    }
}