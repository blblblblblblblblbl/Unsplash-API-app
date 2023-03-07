package com.blblblbl.profile.domain

import com.blblblbl.profile.domain.repository.UserRepository
import com.blblblbl.profile.domain.usecase.GetUserInfoUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetUserInfoUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() = runTest{
        val repository = mock<UserRepository>()
        val usecase = GetUserInfoUseCase(repository)
        val username = "hjfegkslngkjertghjk;nes"
        val usernameCaptor = argumentCaptor<String>()
        launch { usecase.execute(username) }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).getPublicUserInfo(usernameCaptor.capture())
        Assert.assertEquals(username.hashCode(),usernameCaptor.firstValue.hashCode())
    }
}