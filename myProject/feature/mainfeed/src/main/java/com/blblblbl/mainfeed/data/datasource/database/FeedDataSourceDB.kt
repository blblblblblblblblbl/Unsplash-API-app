package com.blblblbl.mainfeed.data.datasource.database

import androidx.paging.PagingSource
import com.blblblbl.mainfeed.data.database.PhotoDataBase
import com.blblblbl.mainfeed.data.model.photos.Photo

interface FeedDataSourceDB {
    fun getPhotosPagingSource(): PagingSource<Int, Photo>
    fun getDB(): PhotoDataBase
}