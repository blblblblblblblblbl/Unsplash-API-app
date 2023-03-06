package com.blblblbl.mainfeed.data.repository

import androidx.paging.*
import com.blblblbl.mainfeed.data.datasource.database.FeedDataSourceDB
import com.blblblbl.mainfeed.data.datasource.network.FeedDataSourceNet
import com.blblblbl.mainfeed.data.utils.mapToDomain
import com.blblblbl.mainfeed.domain.model.photos.Photo
import com.blblblbl.mainfeed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSourceNet: FeedDataSourceNet,
    private val feedDataSourceDB: FeedDataSourceDB
):FeedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllImgs(pageSize: Int): Flow<PagingData<Photo>> {
        val pagingSourceFactory = { feedDataSourceDB.getPhotosPagingSource()}
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = UnsplashRemoteMediator(
                feedDataSourceNet = feedDataSourceNet,
                feedDataSourceDB = feedDataSourceDB

            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { photo->photo.mapToDomain()?:Photo()
            }
        }
    }

    override suspend fun like(id: String){
        feedDataSourceNet.like(id)
    }
    override suspend fun unlike(id: String){
        feedDataSourceNet.unlike(id)
    }
}