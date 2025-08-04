package com.paris_2.san3a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.paris_2.san3a.presentation.San3aScaffold
import com.paris_2.san3a.presentation.utill.InstallSavedAppLanguage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            San3aScaffold()
        }
    }
}