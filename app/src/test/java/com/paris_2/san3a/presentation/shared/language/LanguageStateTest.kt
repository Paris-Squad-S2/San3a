package com.paris_2.san3a.presentation.shared.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import androidx.compose.runtime.CompositionLocalProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class LanguageStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLanguageStateChanges() {
        val languageState = mutableStateOf("en")
        var currentLanguage = ""

        composeTestRule.setContent {
            CompositionLocalProvider(LocalAppLanguage provides languageState) {
                TestLanguageComponent {
                    currentLanguage = it
                }
            }
        }

        // Initial language should be English
        composeTestRule.waitForIdle()
        assertEquals("en", currentLanguage)

        // Change language to Arabic
        composeTestRule.runOnUiThread {
            languageState.value = "ar"
        }

        composeTestRule.waitForIdle()
        assertEquals("ar", currentLanguage)
    }
    
    @Test
    fun testLanguageStateDefaultValue() {
        var currentLanguage = ""
        
        composeTestRule.setContent {
            // Test without providing explicit value - should use default
            TestLanguageComponent {
                currentLanguage = it
            }
        }
        
        composeTestRule.waitForIdle()
        assertEquals("en", currentLanguage) // Should default to English
    }
    
    @Test
    fun testMultipleLanguageChanges() {
        val languageState = mutableStateOf("en")
        var currentLanguage = ""

        composeTestRule.setContent {
            CompositionLocalProvider(LocalAppLanguage provides languageState) {
                TestLanguageComponent {
                    currentLanguage = it
                }
            }
        }

        // Test multiple language switches
        val languages = listOf("en", "ar", "en", "ar")
        
        languages.forEach { language ->
            composeTestRule.runOnUiThread {
                languageState.value = language
            }
            composeTestRule.waitForIdle()
            assertEquals(language, currentLanguage)
        }
    }
}

@Composable
private fun TestLanguageComponent(onLanguageChanged: (String) -> Unit) {
    val language = currentAppLanguage()
    onLanguageChanged(language)
}