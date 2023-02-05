package com.blblblbl.myapplication.data.repository.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class LikedPhotosPagingSource @Inject constructor(
    private val repository: Repository
): PagingSource<Int, Photo>() {
    lateinit var userName:String
    fun userNameinit(userName:String)
    {
        this.userName = userName
    }
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int?= FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getLikedImgs(page, userName)
        }.fold(
            onSuccess = {
                Log.d("MyLog",it.toString())
                if (it.size==0){
                    LoadResult.Page(
                        data = listOf<Photo>(),
                        prevKey = null,
                        nextKey =  null
                    )
                }
                else{
                    it?.let { it1 ->
                        LoadResult.Page(
                            data = it1,
                            prevKey = null,
                            nextKey =  page+1
                        )
                    }
                }

            },
            onFailure = { LoadResult.Error(it) }
        )!!
    }
    companion object{
        private val FIRST_PAGE = 1
    }
}