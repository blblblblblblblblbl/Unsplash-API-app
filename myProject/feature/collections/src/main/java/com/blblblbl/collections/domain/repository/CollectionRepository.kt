package com.blblblbl.collections.domain.repository

import androidx.paging.PagingData
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.domain.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollectionPhotosPagingDataFlow(id: String, pageSize: Int): Flow<PagingData<Photo>>
    fun getCollectionPagingDataFlow(pageSize: Int): Flow<PagingData<PhotoCollection>>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}