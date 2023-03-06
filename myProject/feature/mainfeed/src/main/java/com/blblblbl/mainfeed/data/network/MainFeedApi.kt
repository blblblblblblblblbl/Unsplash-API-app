package com.blblblbl.mainfeed.data.network

import com.blblblbl.mainfeed.data.model.photos.Photo
import retrofit2.http.*

interface MainFeedApi {

    @Headers("mock:true")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<Photo>

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String)
}