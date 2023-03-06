package com.blblblbl.collections.data.datasource

import com.blblblbl.collections.data.model.collection.PhotoCollection
import com.blblblbl.collections.data.model.photo.Photo
import com.blblblbl.collections.data.network.CollectionApi
import javax.inject.Inject

class CollectionDataSourceImpl @Inject constructor(
    private val collectionApi: CollectionApi
):CollectionDataSource {
    override suspend fun getCollectionPage(page: Int): List<PhotoCollection> =
        collectionApi.getCollection(page)

    override suspend fun getCollectionPhotoList(id: String, page: Int): List<Photo> =
        collectionApi.getCollectionPhotos(id,page)

    override suspend fun like(id: String) =
        collectionApi.like(id)

    override suspend fun unlike(id: String) =
        collectionApi.unlike(id)
}