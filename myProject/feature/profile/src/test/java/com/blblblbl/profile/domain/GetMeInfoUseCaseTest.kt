package com.blblblbl.profile.domain

import com.blblblbl.profile.domain.repository.UserRepository
import com.blblblbl.profile.domain.usecase.GetMeInfoUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetMeInfoUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() = runTest{
        val repository = mock<UserRepository>()
        val useCase = GetMeInfoUseCase(repository)
        launch {
            useCase.execute()
        }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).getMeInfo()
    }
}