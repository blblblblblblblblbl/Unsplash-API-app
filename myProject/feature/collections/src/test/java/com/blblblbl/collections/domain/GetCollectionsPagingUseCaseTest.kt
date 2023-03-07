package com.blblblbl.collections.domain

import com.blblblbl.collections.domain.repository.CollectionRepository
import com.blblblbl.collections.domain.usecase.GetCollectionsPagingUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetCollectionsPagingUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<CollectionRepository>()
        val useCase = GetCollectionsPagingUseCase(repository)
        val pageSizeCaptor = argumentCaptor<Int>()
        val pageSize = 10
        useCase.execute(pageSize)
        Mockito.verify(repository, times(1)).getCollectionPagingDataFlow(pageSizeCaptor.capture())
        Assert.assertEquals( pageSize.hashCode(),pageSizeCaptor.firstValue.hashCode())
    }
}