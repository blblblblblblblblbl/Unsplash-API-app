package com.blblblbl.myapplication.data.repository.repository_api

import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.user_info.UserInfo

interface RepositoryApi {
    suspend fun getPhotosPage(page: Int): List<Photo>
    suspend fun getPhotosPage(page: Int, perPage: Int): List<Photo>
    suspend fun searchPhotos(page: Int, perPage: Int = 10, query: String): List<Photo>
    suspend fun getCollectionPage(page: Int): List<PhotoCollection>
    suspend fun getUserInfo(): UserInfo
    suspend fun getPublicUserInfo(username: String): PublicUserInfo
    suspend fun getLikedPhotosPage(page: Int, username: String): List<Photo>
    suspend fun getPhotoById(id: String): DetailedPhotoInfo
    suspend fun getCollectionPhotoList(id: String, page: Int): List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}