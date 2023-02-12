package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.GetLikedPhotoPagingUseCase
import com.blblblbl.myapplication.domain.usecase.GetMeInfoUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetMeInfoUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() = runTest{
        val repository = mock<Repository>()
        val useCase = GetMeInfoUseCase(repository)
        launch {
            useCase.execute()
        }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).getMeInfo()
    }
}