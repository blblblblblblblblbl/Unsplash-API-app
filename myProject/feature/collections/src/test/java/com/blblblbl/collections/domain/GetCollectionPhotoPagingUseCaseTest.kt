package com.blblblbl.collections.domain

import com.blblblbl.collections.domain.repository.CollectionRepository
import com.blblblbl.collections.domain.usecase.GetCollectionPhotoPagingUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetCollectionPhotoPagingUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<CollectionRepository>()
        val useCase = GetCollectionPhotoPagingUseCase(repository)
        val idCaptor = argumentCaptor<String>()
        val pageSizeCaptor = argumentCaptor<Int>()
        val id = "id"
        val pageSize = 10
        useCase.execute(id,pageSize)
        Mockito.verify(repository, times(1)).getCollectionPhotosPagingDataFlow(idCaptor.capture(),pageSizeCaptor.capture())
        Assert.assertEquals( id.hashCode(),idCaptor.firstValue.hashCode())
        Assert.assertEquals( pageSize.hashCode(),pageSizeCaptor.firstValue.hashCode())
    }
}