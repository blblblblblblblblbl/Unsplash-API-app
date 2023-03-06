package com.blblblbl.mainfeed.data.datasource.network

import com.blblblbl.mainfeed.data.model.photos.Photo

interface FeedDataSourceNet {
    suspend fun getPhotosPage(page: Int, perPage: Int): List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}