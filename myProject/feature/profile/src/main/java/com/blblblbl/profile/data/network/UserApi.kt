package com.blblblbl.profile.data.network

import com.blblblbl.profile.data.model.photo.Photo
import com.blblblbl.profile.data.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.data.model.user_info.UserInfo
import retrofit2.http.*

interface UserApi {
    @GET("me")
    suspend fun getMe(): UserInfo

    @GET("/users/{username}")
    suspend fun getPublicUserInfo(
        @Path("username") username: String
    ): PublicUserInfo

    @Headers("mock:true")
    @GET("users/{username}/likes")
    suspend fun getLikedPhotos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<Photo>

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String)
}