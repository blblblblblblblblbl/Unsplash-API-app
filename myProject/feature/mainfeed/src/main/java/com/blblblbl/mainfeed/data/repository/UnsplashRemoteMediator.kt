package com.blblblbl.mainfeed.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blblblbl.mainfeed.data.database.entity.DBPhoto
import com.blblblbl.mainfeed.data.database.entity.UnsplashRemoteKeys
import com.blblblbl.mainfeed.data.datasource.database.FeedDataSourceDB
import com.blblblbl.mainfeed.data.datasource.network.FeedDataSourceNet
import com.blblblbl.mainfeed.data.model.photos.Photo

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val feedDataSourceNet: FeedDataSourceNet,
    private val feedDataSourceDB: FeedDataSourceDB
) : RemoteMediator<Int, Photo>() {

    private val unsplashImageDao = feedDataSourceDB.getDB().photoDao()
    private val unsplashRemoteKeysDao = feedDataSourceDB.getDB().unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Photo>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = feedDataSourceNet.getPhotosPage(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            feedDataSourceDB.getDB().withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.clear()
                    unsplashRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id!!,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                response.forEach{photo->
                    unsplashImageDao.insert(DBPhoto(photo.id!!,photo))
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Photo>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Photo>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashImage.id?.let { unsplashRemoteKeysDao.getRemoteKeys(id = it) }
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Photo>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashImage.id?.let { unsplashRemoteKeysDao.getRemoteKeys(id = it) }
            }
    }

    companion object{
        const val ITEMS_PER_PAGE:Int = 10
    }

}