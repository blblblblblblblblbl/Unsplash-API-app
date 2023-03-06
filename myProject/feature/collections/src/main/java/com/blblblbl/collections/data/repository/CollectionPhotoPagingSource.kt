package com.blblblbl.collections.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.collections.data.datasource.CollectionDataSource
import com.blblblbl.collections.data.model.photo.Photo
import javax.inject.Inject

class CollectionPhotoPagingSource @Inject constructor(
    private val collectionDataSource: CollectionDataSource
): PagingSource<Int, Photo>() {
    private lateinit var id:String
    fun idInit(id:String){
        this.id = id
    }
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            collectionDataSource.getCollectionPhotoList(id,page)
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