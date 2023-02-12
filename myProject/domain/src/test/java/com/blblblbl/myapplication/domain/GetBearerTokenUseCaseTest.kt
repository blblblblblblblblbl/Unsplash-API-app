package com.blblblbl.myapplication.domain

import androidx.core.net.toUri
import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.GetBearerTokenUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetBearerTokenUseCaseTest {
    @Test
    fun workIsCorrect() {
        val repository = mock<Repository>()
        val useCase = GetBearerTokenUseCase(repository)
        val code = "12345546"
        val redirectUri = (GetBearerTokenUseCase.START+code).toUri()
        val argCaptor = argumentCaptor<String>()
        useCase.execute(redirectUri)
        Mockito.verify(repository, times(1)).authorize(argCaptor.capture())
        Assert.assertEquals(code,argCaptor.firstValue)
    }
}