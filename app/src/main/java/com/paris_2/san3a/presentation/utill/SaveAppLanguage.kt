package com.paris_2.san3a.presentation.utill

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.paris_2.san3a.presentation.screen.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun InstallSavedAppLanguage(
    context: Context,
    mainViewModel: MainViewModel = koinViewModel()
){

    LaunchedEffect(key1 = Unit){
        mainViewModel.getLastSelectedAppLanguage().collectLatest {language ->
            updateResources(context = context, localeLanguage = Locale(language))
        }
    }
}

fun updateResources(context: Context, localeLanguage: Locale) {
    Locale.setDefault(localeLanguage)
    val resources: Resources = context.resources
    val config = Configuration(resources.configuration)
    config.setLocale(localeLanguage)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        config.setLayoutDirection(localeLanguage)
    }

    context.createConfigurationContext(config)
    @Suppress("DEPRECATION")
    resources.updateConfiguration(config, resources.displayMetrics)
}