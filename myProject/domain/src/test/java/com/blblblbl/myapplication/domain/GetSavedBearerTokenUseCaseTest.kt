package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetSavedBearerTokenUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect(){
        val repository = mock<Repository>()
        val useCase = GetSavedBearerTokenUseCase(repository)
        useCase.execute()
        Mockito.verify(repository, times(1)).getSavedBearerToken()
    }
}