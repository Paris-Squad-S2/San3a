package com.paris_2.san3a.presentation.mapper

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.screen.more.UserUiState

fun User.toUserUiState(): UserUiState {
    return UserUiState(
        phoneNumber = this.phone,
        name = this.fullName,
        isCraftsman = this.accountType == AccountType.CRAFTSMAN,
        imageUrl = this.profilePhoto,
        rating = this.rating,
        review = this.review
    )
}