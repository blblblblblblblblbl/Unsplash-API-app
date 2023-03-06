package com.blblblbl.collections.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.blblblbl.collections.data.datasource.CollectionDataSource
import com.blblblbl.collections.data.utils.mapToDomain
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.domain.model.photo.Photo
import com.blblblbl.collections.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDataSource: CollectionDataSource,
    private val collectionPhotosPagingSource: CollectionPhotoPagingSource,
    private val collectionsPagingSource: CollectionsPagingSource,
):CollectionRepository {
    override fun getCollectionPhotosPagingDataFlow(
        id: String,
        pageSize: Int
    ): Flow<PagingData<Photo>> {
        collectionPhotosPagingSource.idInit(id)
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { collectionPhotosPagingSource }
        ).flow.map { pagingData->
            pagingData.map { it.mapToDomain()?:Photo() }
        }
    }

    override fun getCollectionPagingDataFlow(pageSize: Int): Flow<PagingData<PhotoCollection>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { collectionsPagingSource }
        ).flow.map { pagingData->
            pagingData.map { it.mapToDomain()?: PhotoCollection() }
        }
    }

    override suspend fun like(id: String){
        collectionDataSource.like(id)
    }
    override suspend fun unlike(id: String){
        collectionDataSource.unlike(id)
    }
}