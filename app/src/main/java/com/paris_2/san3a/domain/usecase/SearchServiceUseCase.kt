package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.ServicesRepository

class SearchServiceUseCase(
    private val servicesRepository: ServicesRepository
) {
    operator fun invoke(query: String) = servicesRepository.searchServices(query)
}