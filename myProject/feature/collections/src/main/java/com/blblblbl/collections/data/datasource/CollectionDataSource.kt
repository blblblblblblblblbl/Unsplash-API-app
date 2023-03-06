package com.blblblbl.collections.data.datasource

import com.blblblbl.collections.data.model.collection.PhotoCollection
import com.blblblbl.collections.data.model.photo.Photo

interface CollectionDataSource {

    suspend fun getCollectionPage(page: Int): List<PhotoCollection>
    suspend fun getCollectionPhotoList(id: String, page: Int): List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}