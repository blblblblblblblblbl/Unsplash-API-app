package com.blblblbl.mainfeed.domain

import com.blblblbl.mainfeed.domain.repository.FeedRepository
import com.blblblbl.mainfeed.domain.usecase.GetPhotosFeedUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetPhotosFeedUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<FeedRepository>()
        val useCase = GetPhotosFeedUseCase(repository)
        val pageSizeCaptor = argumentCaptor<Int>()
        val pageSize = 10
        useCase.execute(pageSize)
        Mockito.verify(repository, times(1)).getAllImgs(pageSizeCaptor.capture())
        Assert.assertEquals( pageSize.hashCode(),pageSizeCaptor.firstValue.hashCode())
    }
}