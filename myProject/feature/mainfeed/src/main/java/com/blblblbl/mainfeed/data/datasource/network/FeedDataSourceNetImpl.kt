package com.blblblbl.mainfeed.data.datasource.network

import com.blblblbl.mainfeed.data.model.photos.Photo
import com.blblblbl.mainfeed.data.network.MainFeedApi
import javax.inject.Inject

class FeedDataSourceNetImpl @Inject constructor(
    private val mainFeedApi: MainFeedApi
):FeedDataSourceNet {
    override suspend fun getPhotosPage(page: Int, perPage: Int): List<Photo> =
        mainFeedApi.getPhotos(page, perPage)

    override suspend fun like(id: String) =
        mainFeedApi.like(id)

    override suspend fun unlike(id: String) =
        mainFeedApi.unlike(id)
}