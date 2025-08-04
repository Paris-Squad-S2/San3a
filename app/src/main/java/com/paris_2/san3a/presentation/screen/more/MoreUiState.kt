package com.paris_2.san3a.presentation.screen.more

data class MoreUiState(
    val imageUrl: String = "",
    val name: String = "",
    val review: Int = 0,
    val rating: Double = 0.0,
    val phoneNumber: String = "",
    val isDarkMode: Boolean = false,
    val isVerify: Boolean = false,
    val isCraftsman: Boolean = false,
    val versionNumber: String = "0.0.0"
)