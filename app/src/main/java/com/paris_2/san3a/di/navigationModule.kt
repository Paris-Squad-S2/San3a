package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.navigation.Navigator
import com.paris_2.san3a.presentation.navigation.NavigatorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::NavigatorImpl) { bind<Navigator>() }
}