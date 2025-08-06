package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.ProfileRepository

class GetVersionNameUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): String{
        return profileRepository.getVersionName()
    }
}