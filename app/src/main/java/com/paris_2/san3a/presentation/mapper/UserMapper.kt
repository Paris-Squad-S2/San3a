package com.paris_2.san3a.presentation.mapper

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.screen.more.UserUiState
import androidx.core.net.toUri

fun User.toUserUiState(): UserUiState {
    return UserUiState(
        phoneNumber = this.phone,
        name = this.fullName,
        isCraftsman = this.accountType == AccountType.CRAFTSMAN,
        imageUrl = this.profilePhoto.toUri(),
    )
}