package com.blblblbl.detailedphoto.domain

import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import com.blblblbl.detailedphoto.domain.usecase.DownloadImgUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class DownloadImgUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<DetailedPhotoRepository>()
        val useCase = DownloadImgUseCase(repository)
        val detailedPhotoInfo = DetailedPhotoInfo()
        val argCaptor = argumentCaptor<DetailedPhotoInfo>()
        useCase.execute(detailedPhotoInfo)
        Mockito.verify(repository, times(1)).downloadImg(argCaptor.capture())
        Assert.assertEquals( detailedPhotoInfo.hashCode(),argCaptor.firstValue.hashCode())
    }
}