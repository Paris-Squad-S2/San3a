package com.paris_2.san3a.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.presentation.screen.account.components.LocationWebView
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun San3aScaffold() {
    San3aTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            LocationWebView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun San3aScaffoldPreview() {
    San3aScaffold()
}