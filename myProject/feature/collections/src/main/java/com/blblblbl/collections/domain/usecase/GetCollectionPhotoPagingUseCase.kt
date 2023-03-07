package com.blblblbl.collections.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.collections.domain.model.photo.Photo
import com.blblblbl.collections.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionPhotoPagingUseCase @Inject constructor(
    private val repository: CollectionRepository
) {

    fun execute(id: String, pageSize: Int): Flow<PagingData<Photo>> =
        repository.getCollectionPhotosPagingDataFlow(id, pageSize)

}