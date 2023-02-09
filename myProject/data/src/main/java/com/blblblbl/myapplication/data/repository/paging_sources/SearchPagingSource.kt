package com.blblblbl.myapplication.data.repository.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.utils.mapToDomain

class SearchPagingSource(
    private val repositoryApi: RepositoryApi,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPage = params.key?: FIRST_PAGE
        return try {
            val responseApi = repositoryApi.searchPhotos(page = currentPage, perPage = ITEMS_PER_PAGE, query = query )
            val response = mutableListOf<Photo>()
            responseApi.forEach { it.mapToDomain()?.let { it1 -> response.add(it1) } }
            Log.d("MyLog","search response: $response")
            val endOfPaginationReached = response.isEmpty()
            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            Log.d("MyLog","search response exception: ${e.message}")
            LoadResult.Error(e)
        }
    }


    companion object{
        const val ITEMS_PER_PAGE = 10
        const val FIRST_PAGE = 1
    }
}