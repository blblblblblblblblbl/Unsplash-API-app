package com.blblblbl.detailedphoto.data.datasource

import com.blblblbl.detailedphoto.data.model.photo_detailed.DetailedPhotoInfo

interface DetailedPhotoDataSource {
    suspend fun getPhotoById(id: String): DetailedPhotoInfo
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}