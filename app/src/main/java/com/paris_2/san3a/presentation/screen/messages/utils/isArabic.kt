package com.paris_2.san3a.presentation.screen.messages.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

object ArabicStringMapper {

    @SuppressLint("LocalContextConfigurationRead")
    @Composable
    fun isArabicLanguage(): Boolean {
        val context = LocalContext.current
        val currentLocale: Locale = LocalContext.current.resources.configuration.locales[0]
        return currentLocale.language == "ar"
    }

    fun String.toArabicNumerals(): String {
        val arabicDigits = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        return this.map { digit ->
            if (digit in '0'..'9') arabicDigits[digit - '0'] else digit
        }.joinToString("")
    }

    private val stringToArabic = mapOf(

        "january" to "يناير",
        "february" to "فبراير",
        "march" to "مارس",
        "april" to "أبريل",
        "may" to "مايو",
        "june" to "يونيو",
        "july" to "يوليو",
        "august" to "أغسطس",
        "september" to "سبتمبر",
        "october" to "أكتوبر",
        "november" to "نوفمبر",
        "december" to "ديسمبر",

        "sunday" to "الأحد",
        "monday" to "الإثنين",
        "tuesday" to "الثلاثاء",
        "wednesday" to "الأربعاء",
        "thursday" to "الخميس",
        "friday" to "الجمعة",
        "saturday" to "السبت",

        "sun" to "أحد",
        "mon" to "إثنين",
        "tue" to "ثلاثاء",
        "wed" to "أربعاء",
        "thu" to "خميس",
        "fri" to "جمعة",
        "sat" to "سبت",

    )


    fun String?.toArabicSafe(): String {
        return this?.trim()?.lowercase()?.let { stringToArabic[it] } ?: this.orEmpty()
    }

}
