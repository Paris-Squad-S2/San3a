package com.paris_2.san3a.presentation.utill

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.LocaleListCompat
import com.paris_2.san3a.presentation.screen.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InstallSavedAppLanguage(
    context: Context,
    mainViewModel: MainViewModel = koinViewModel()
){
    LaunchedEffect(key1 = Unit) {
        mainViewModel.getLastSelectedAppLanguage().collectLatest { language ->
            withContext(Dispatchers.Main) {
                changeAppLanguage(
                    context = context,
                    language
                )
            }
        }
    }
}

private fun changeAppLanguage(context: Context, languageCode: String) {
    val localeListCompat = LocaleListCompat.forLanguageTags(languageCode)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = context.getSystemService(android.app.LocaleManager::class.java)
        localeManager?.applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(localeListCompat)
    }
}