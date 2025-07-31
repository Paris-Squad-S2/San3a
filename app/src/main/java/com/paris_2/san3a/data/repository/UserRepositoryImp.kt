package com.paris_2.san3a.data.repository

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import com.paris_2.san3a.domain.repository.UserRepository
import com.paris_2.san3a.domain.source.local.UserLocalDataSource

class UserRepositoryImp(
    private val userLocalDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun setAccountType(accountType: AccountType) {
        userLocalDataSource.setAccountType(accountType)
    }

    override suspend fun setUpAccount(user: User) {
        remoteDataSource.setUpAccount(user)
    }
}