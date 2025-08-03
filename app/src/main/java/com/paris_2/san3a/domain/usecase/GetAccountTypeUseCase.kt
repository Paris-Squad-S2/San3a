package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.repository.UserRepository

class GetAccountTypeUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String): AccountType {
        return userRepository.getAccountType(phone)
    }
}