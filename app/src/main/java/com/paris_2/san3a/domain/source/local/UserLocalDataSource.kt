package com.paris_2.san3a.domain.source.local

import com.paris_2.san3a.domain.entity.AccountType

interface UserLocalDataSource {
    suspend fun setAccountType(accountType: AccountType)
    suspend fun getAccountType(): AccountType?
}