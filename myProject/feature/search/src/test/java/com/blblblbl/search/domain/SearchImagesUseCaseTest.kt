package com.blblblbl.search.domain

import com.blblblbl.search.domain.repository.SearchRepository
import com.blblblbl.search.domain.usecase.SearchImagesUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class SearchImagesUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<SearchRepository>()
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