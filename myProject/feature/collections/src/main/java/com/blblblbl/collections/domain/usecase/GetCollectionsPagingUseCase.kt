package com.blblblbl.collections.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsPagingUseCase @Inject constructor(
    private val repository: CollectionRepository
) {

    fun execute(pageSize: Int): Flow<PagingData<PhotoCollection>> =
        repository.getCollectionPagingDataFlow(pageSize)
}