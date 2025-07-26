package com.paris_2.san3a.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme

@Composable
fun San3aScaffold() {
    San3aTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text(
                text = "Hello Android!",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun San3aScaffoldPreview() {
    San3aScaffold()
}