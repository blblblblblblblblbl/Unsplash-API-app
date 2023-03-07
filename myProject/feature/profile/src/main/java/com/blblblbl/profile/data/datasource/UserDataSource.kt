package com.blblblbl.profile.data.datasource

import com.blblblbl.profile.data.model.photo.Photo
import com.blblblbl.profile.data.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.data.model.user_info.UserInfo

interface UserDataSource {
    suspend fun getUserInfo(): UserInfo
    suspend fun getPublicUserInfo(username: String): PublicUserInfo
    suspend fun getLikedPhotosPage(page: Int, username: String): List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}