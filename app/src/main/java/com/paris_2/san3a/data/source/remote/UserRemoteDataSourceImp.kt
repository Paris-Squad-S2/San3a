package com.paris_2.san3a.data.source.remote

import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRemoteDataSource

class UserRemoteDataSourceImp(
    private val fireStoreService: FireStoreService,
) : UserRemoteDataSource {
    override suspend fun setUpAccount(user: User) {
        fireStoreService.setDoc(documentPath = "$USERS_COLLECTION/${user.phone}", data = user)
    }

    companion object {
        const val USERS_COLLECTION = "users"
    }
}