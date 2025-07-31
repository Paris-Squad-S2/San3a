package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User

interface UserRepository {
    suspend fun setAccountType(accountType: AccountType)
    suspend fun setUpAccount(user: User)
}