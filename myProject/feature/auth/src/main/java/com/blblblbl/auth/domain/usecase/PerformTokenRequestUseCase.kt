package com.blblblbl.auth.domain.usecase

import com.blblblbl.auth.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class PerformTokenRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) = authRepository.performTokenRequest(authService, tokenRequest)
}