package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.SearchImagesUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class SearchImagesUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<Repository>()
        val useCase = SearchImagesUseCase(repository)
        val queryCaptor = argumentCaptor<String>()
        val pageSizeCaptor = argumentCaptor<Int>()
        val id = "id"
        val pageSize = 10
        useCase.execute(id,pageSize)
        Mockito.verify(repository, times(1)).searchImgByTags(queryCaptor.capture(),pageSizeCaptor.capture())
        Assert.assertEquals( id.hashCode(),queryCaptor.firstValue.hashCode())
        Assert.assertEquals( pageSize.hashCode(),pageSizeCaptor.firstValue.hashCode())
    }
}