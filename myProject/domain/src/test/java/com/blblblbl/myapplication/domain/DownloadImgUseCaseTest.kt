package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.DownloadImgUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class DownloadImgUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<Repository>()
        val useCase = DownloadImgUseCase(repository)
        val detailedPhotoInfo = DetailedPhotoInfo()
        val argCaptor = argumentCaptor<DetailedPhotoInfo>()
        useCase.execute(detailedPhotoInfo)
        Mockito.verify(repository, times(1)).downloadImg(argCaptor.capture())
        Assert.assertEquals( detailedPhotoInfo.hashCode(),argCaptor.firstValue.hashCode())
    }
}