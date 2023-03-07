package com.blblblbl.detailedphoto.domain.repository

import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo

interface DetailedPhotoRepository {
    suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo
    fun downloadImg(detailedPhotoInfo: DetailedPhotoInfo)
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}