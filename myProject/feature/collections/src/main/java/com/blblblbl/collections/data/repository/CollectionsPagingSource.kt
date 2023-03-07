package com.blblblbl.collections.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.collections.data.datasource.CollectionDataSource
import com.blblblbl.collections.data.model.collection.PhotoCollection
import javax.inject.Inject

class CollectionsPagingSource@Inject constructor(
    private val collectionDataSource: CollectionDataSource
): PagingSource<Int, PhotoCollection>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoCollection>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoCollection> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            collectionDataSource.getCollectionPage(page)
        }.fold(
            onSuccess = {
                it?.let { it1 ->
                    LoadResult.Page(
                        data = it1,
                        prevKey = null,
                        nextKey =  page+1
                    )
                }
            },
            onFailure = { LoadResult.Error(it) }
        )!!
    }
    companion object{
        private val FIRST_PAGE = 1
    }
}