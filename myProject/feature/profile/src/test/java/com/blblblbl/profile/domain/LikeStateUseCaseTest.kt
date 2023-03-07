package com.blblblbl.profile.domain

import com.blblblbl.profile.domain.repository.UserRepository
import com.blblblbl.profile.domain.usecase.LikeStateUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class LikeStateUseCaseTest {
    @Test
    fun likeRepositoryUsageIsCorrect() = runTest{
        val repository = mock<UserRepository>()
        val usecase = LikeStateUseCase(repository)
        val id = "werfghujnigwen;jk"
        val idCaptor = argumentCaptor<String>()
        launch { usecase.like(id) }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).like(idCaptor.capture())
        Assert.assertEquals(id.hashCode(),idCaptor.firstValue.hashCode())
    }
    @Test
    fun unlikeRepositoryUsageIsCorrect() = runTest{
        val repository = mock<UserRepository>()
        val usecase = LikeStateUseCase(repository)
        val id = "werfghujnigwen;jk"
        val idCaptor = argumentCaptor<String>()
        launch { usecase.unlike(id) }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).unlike(idCaptor.capture())
        Assert.assertEquals(id.hashCode(),idCaptor.firstValue.hashCode())
    }
}