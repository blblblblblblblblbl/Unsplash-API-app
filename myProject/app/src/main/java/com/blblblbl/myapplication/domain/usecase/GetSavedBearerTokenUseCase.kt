package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class GetSavedBearerTokenUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute():String?{
        return repository.getSavedBearerToken()
    }
}