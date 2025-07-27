package com.paris_2.san3a.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.presentation.navigation.San3aNavGraph
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme

@Composable
fun San3aScaffold() {
    San3aTheme {
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            San3aNavGraph()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun San3aScaffoldPreview() {
    San3aScaffold()
}