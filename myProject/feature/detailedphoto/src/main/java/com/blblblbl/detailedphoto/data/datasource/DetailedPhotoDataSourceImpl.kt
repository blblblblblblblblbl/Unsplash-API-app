package com.blblblbl.detailedphoto.data.datasource

import com.blblblbl.detailedphoto.data.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.data.network.DetailedPhotoApi
import javax.inject.Inject

class DetailedPhotoDataSourceImpl @Inject constructor(
    private val detailedPhotoApi: DetailedPhotoApi
)
    :DetailedPhotoDataSource {
    override suspend fun getPhotoById(id: String): DetailedPhotoInfo =
        detailedPhotoApi.getPhotoById(id)

    override suspend fun like(id: String) =
        detailedPhotoApi.like(id)

    override suspend fun unlike(id: String) =
        detailedPhotoApi.unlike(id)
}