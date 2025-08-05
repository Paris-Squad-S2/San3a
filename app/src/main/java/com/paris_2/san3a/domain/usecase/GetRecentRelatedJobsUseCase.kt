package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class GetRecentRelatedJobsUseCase(
    private val userRepository: UserRepository
) {
   operator fun invoke(relatedJobs: List<String>) = userRepository.getRecentRelatedJobs(relatedJobs)
}