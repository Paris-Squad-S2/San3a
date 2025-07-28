package com.paris_2.san3a.presentation.screen.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.account.components.AccountProgressIndicator

@Composable
fun AccountSetup(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(0) }
    val stepsCount = 4
    val progress = remember(currentScreen) {
        (currentScreen + 1) / stepsCount.toFloat()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountProgressIndicator(progress = progress, modifier = Modifier.width(200.dp))
        }

    }
}


@Preview
@Composable
private fun AccountSetupPrev() {
    AccountSetup()
}