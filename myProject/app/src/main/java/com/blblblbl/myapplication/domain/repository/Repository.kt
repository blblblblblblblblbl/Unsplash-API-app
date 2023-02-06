package com.blblblbl.myapplication.domain.repository

import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getImgs(page: Int):List<Photo>
    fun getAllImgs(): Flow<PagingData<DBPhoto>>
    suspend fun getCollectionImgList(id:String, page: Int):List<Photo>

    suspend fun getLikedImgs(page: Int, userName:String):List<Photo>
    suspend fun getCollections(page: Int):List<PhotoCollection>

    fun searchImgByTags(query: String): Flow<PagingData<Photo>>
    suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo

    suspend fun getMeInfo(): UserInfo?
    suspend fun getPublicUserInfo(username:String): PublicUserInfo

    suspend fun like(id: String)
    suspend fun unlike(id: String)

    suspend fun clearDB()
}