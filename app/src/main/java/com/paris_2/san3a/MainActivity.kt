package com.paris_2.san3a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paris_2.san3a.presentation.San3aScaffold
import com.paris_2.san3a.presentation.util.ActivityProvider
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val activityProvider: ActivityProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityProvider.setCurrentActivity(this)
        setContent {
            San3aScaffold()
        }
    }
}