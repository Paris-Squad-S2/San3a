package com.paris_2.san3a.presentation.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.language.currentAppLanguage
import com.paris_2.san3a.presentation.shared.language.rememberAppLanguage
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

/**
 * Demo component showing how language changes work immediately across the app
 */
@Composable
fun LanguageChangeDemo() {
    val appLanguageState = rememberAppLanguage()
    val currentLanguage = currentAppLanguage()
    val layoutDirection = LocalLayoutDirection.current
    val configuration = LocalConfiguration.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Current Language: $currentLanguage",
                style = Theme.textStyle.body.medium.medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Layout Direction: ${if (layoutDirection == LayoutDirection.Rtl) "RTL" else "LTR"}",
                style = Theme.textStyle.body.small.regular
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Locale: ${configuration.locales[0]}",
                style = Theme.textStyle.body.small.regular
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Demo string resource that changes based on language
            Text(
                text = stringResource(R.string.change_language),
                style = Theme.textStyle.title.small
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    // Toggle language - this demonstrates immediate change
                    appLanguageState.value = if (currentLanguage == "en") "ar" else "en"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Toggle Language (Current: $currentLanguage)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageChangeDemoPreview() {
    val demoLanguageState = remember { mutableStateOf("en") }
    
    androidx.compose.runtime.CompositionLocalProvider(
        com.paris_2.san3a.presentation.shared.language.LocalAppLanguage provides demoLanguageState
    ) {
        LanguageChangeDemo()
    }
}