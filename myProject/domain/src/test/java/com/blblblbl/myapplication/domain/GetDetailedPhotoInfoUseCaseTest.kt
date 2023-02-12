package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.GetCollectionPhotoPagingUseCase
import com.blblblbl.myapplication.domain.usecase.GetDetailedPhotoInfoUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetDetailedPhotoInfoUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() = runTest {
        val repository = mock<Repository>()
        val useCase = GetDetailedPhotoInfoUseCase(repository)
        val idCaptor = argumentCaptor<String>()
        val id = "id"
        launch { useCase.execute(id)}
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).getDetailedImgInfoById(idCaptor.capture())
        Assert.assertEquals( id.hashCode(),idCaptor.firstValue.hashCode())
    }
}