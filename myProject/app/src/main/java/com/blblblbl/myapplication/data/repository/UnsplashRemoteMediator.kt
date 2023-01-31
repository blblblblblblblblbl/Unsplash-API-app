package com.blblblbl.myapplication.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.data.repository.database.entities.UnsplashRemoteKeys
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_db.RepositoryDataBase

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val repositoryApi: RepositoryApi,
    private val repositoryDataBase: RepositoryDataBase
) : RemoteMediator<Int, DBPhoto>() {

    private val unsplashImageDao = repositoryDataBase.db.photoDao()// unsplashDatabase.unsplashImageDao()
    private val unsplashRemoteKeysDao = repositoryDataBase.db.unsplashRemoteKeysDao()//unsplashDatabase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DBPhoto>
    ): MediatorResult {
        return try {
            Log.d("MyLog","loadType: $loadType")
            Log.d("MyLog","state: ${state}")
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

            val response = repositoryApi.getPhotosPage(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            repositoryDataBase.db.withTransaction {
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
        state: PagingState<Int, DBPhoto>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, DBPhoto>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, DBPhoto>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    companion object{
        const val ITEMS_PER_PAGE:Int = 10
    }

}