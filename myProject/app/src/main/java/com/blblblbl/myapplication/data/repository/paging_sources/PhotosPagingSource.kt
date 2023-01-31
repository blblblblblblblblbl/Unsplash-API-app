package com.blblblbl.myapplication.data.repository.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.myapplication.data.repository.Repository
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import javax.inject.Inject

class PhotosPagingSource @Inject constructor(
    private val repository: Repository
):PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getPhotos(page)
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
            onFailure = {LoadResult.Error(it) }
        )!!
    }
    companion object{
        private val FIRST_PAGE = 1
    }
}