package com.blblblbl.profile.domain

import com.blblblbl.profile.domain.repository.UserRepository
import com.blblblbl.profile.domain.usecase.GetLikedPhotoPagingUseCase
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class GetLikedPhotoPagingUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() {
        val repository = mock<UserRepository>()
        val useCase = GetLikedPhotoPagingUseCase(repository)
        val idCaptor = argumentCaptor<String>()
        val pageSizeCaptor = argumentCaptor<Int>()
        val id = "id"
        val pageSize = 10
        useCase.execute(id,pageSize)
        Mockito.verify(repository, times(1)).getLikedPhotosPagingDataFlow(idCaptor.capture(),pageSizeCaptor.capture())
        Assert.assertEquals( id.hashCode(),idCaptor.firstValue.hashCode())
        Assert.assertEquals( pageSize.hashCode(),pageSizeCaptor.firstValue.hashCode())
    }
}