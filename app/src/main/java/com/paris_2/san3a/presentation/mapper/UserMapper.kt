package com.paris_2.san3a.presentation.mapper

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.screen.more.moreScreen.UserUiState
import androidx.core.net.toUri
import com.paris_2.san3a.presentation.utill.fakeImage

fun User.toUserUiState(rating: Float): UserUiState {
    return UserUiState(
        phoneNumber = this.phone,
        name = this.fullName,
        previousText = this.fullName,
        rating = rating,
        isCraftsman = this.accountType == AccountType.CRAFTSMAN,
        imageUrl = if (this.profilePhoto.isNullOrBlank()) fakeImage.toUri() else this.profilePhoto.toUri(),
    )
}