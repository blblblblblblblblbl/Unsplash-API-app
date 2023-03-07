package com.blblblbl.profile.data.datasource

import com.blblblbl.profile.data.model.photo.Photo
import com.blblblbl.profile.data.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.data.model.user_info.UserInfo
import com.blblblbl.profile.data.network.UserApi
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val api:UserApi
):UserDataSource {
    override suspend fun getUserInfo(): UserInfo =
        api.getMe()

    override suspend fun getPublicUserInfo(username: String): PublicUserInfo =
        api.getPublicUserInfo(username)

    override suspend fun getLikedPhotosPage(page: Int, username: String): List<Photo> =
        api.getLikedPhotos(username, page)

    override suspend fun like(id: String) =
        api.like(id)

    override suspend fun unlike(id: String) =
        api.unlike(id)
}