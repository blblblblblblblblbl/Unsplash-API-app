package com.blblblbl.myapplication.data.repository.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.myapplication.domain.repository.Repository
import com.example.example.PhotoCollection
import javax.inject.Inject

class CollectionsPagingSource@Inject constructor(
    private val repository: Repository
): PagingSource<Int, PhotoCollection>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoCollection>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoCollection> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCollections(page)
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