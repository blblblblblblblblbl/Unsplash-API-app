package com.blblblbl.search.data.datasource

import com.blblblbl.search.data.model.photo.Photo

interface SearchDataSource {
    suspend fun searchPhotos(page: Int, perPage: Int = 10, query: String): List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}