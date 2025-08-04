package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.HomeRepository

class SearchServiceUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(query: String) = homeRepository.searchServices(query)
}