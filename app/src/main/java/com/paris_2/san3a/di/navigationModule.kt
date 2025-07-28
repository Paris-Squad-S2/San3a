package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.navigation.Navigator
import com.paris_2.san3a.presentation.navigation.NavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single <Navigator>{ NavigatorImpl(startGraph = Destinations.Graph1) }
}