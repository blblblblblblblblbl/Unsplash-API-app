package com.blblblbl.myapplication.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsPagingUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(pageSize:Int): Flow<PagingData<PhotoCollection>> {
        return repository.getCollectionPagingDataFlow(pageSize)
    }
}