package com.blblblbl.auth.domain.usecase

import com.blblblbl.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthRequestUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    fun execute() = authRepository.getAuthRequest()
}