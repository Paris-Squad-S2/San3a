package com.paris_2.san3a.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.home.customer.CustomerHomeScreen
import com.paris_2.san3a.presentation.screen.messages.MessagesScreen
import com.paris_2.san3a.presentation.screen.messagesDetails.MessageDetailsScreen
import com.paris_2.san3a.presentation.screen.more.moreScreen.MoreScreen
import com.paris_2.san3a.presentation.screen.notification.NotificationScreen
import com.paris_2.san3a.presentation.screen.requestDetails.customer.CustomerRequestDetailsScreen
import com.paris_2.san3a.presentation.screen.requests.customer.MyRequestScreen

fun NavGraphBuilder.buildCustomerNavGraph() {
    navigation<Destinations.CustomerGraph>(startDestination = Destinations.Home) {
        composable<Destinations.Home> { CustomerHomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MessageDetails> { MessageDetailsScreen() }
        composable<Destinations.Requests> { MyRequestScreen() }
        composable<Destinations.RequestDetails> { CustomerRequestDetailsScreen() }
        composable<Destinations.Notification> { NotificationScreen() }
        composable<Destinations.More> { MoreScreen() }
    }
}