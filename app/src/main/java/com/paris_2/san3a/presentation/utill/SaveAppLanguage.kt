package com.paris_2.san3a.presentation.utill

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.paris_2.san3a.presentation.screen.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun InstallSavedAppLanguage(
    context: Context,
    userSettingsViewModel: MainViewModel = koinViewModel()
){

    LaunchedEffect(key1 = Unit){
        userSettingsViewModel.getLastSelectedAppLanguage().collectLatest {language ->
            updateResources(context = context, localeLanguage = Locale(language))
        }
    }
}

private fun updateResources(context: Context, localeLanguage: Locale) {
    Locale.setDefault(localeLanguage)
    val resources = context.resources
    val config = resources.configuration
    config.setLocale(localeLanguage)
    context.createConfigurationContext(config)
}