package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.User

interface UserRemoteDataSource {
    suspend fun setUpAccount(user: User)
}