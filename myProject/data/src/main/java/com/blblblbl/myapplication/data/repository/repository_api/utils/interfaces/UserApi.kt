package com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces

import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.data_classes.user_info.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {
    @GET("me")
    suspend fun getMe(@Header("Authorization") authHeader: String): UserInfo

    @GET("/users/{username}")
    suspend fun getPublicUserInfo(
        @Path("username") username: String,
        @Header("Authorization") authHeader: String
    ): PublicUserInfo
}