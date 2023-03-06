package com.blblblbl.search.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.search.data.datasource.SearchDataSource
import com.blblblbl.search.data.model.photo.Photo

class SearchPagingSource(
    private val searchDataSource: SearchDataSource,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPage = params.key?: FIRST_PAGE
        return try {
            val response = searchDataSource.searchPhotos(page = currentPage, perPage = ITEMS_PER_PAGE, query = query )
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
            LoadResult.Error(e)
        }
    }


    companion object{
        const val ITEMS_PER_PAGE = 10
        const val FIRST_PAGE = 1
    }
}