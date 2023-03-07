package com.blblblbl.detailedphoto.data.network

import com.blblblbl.detailedphoto.data.model.photo_detailed.DetailedPhotoInfo
import retrofit2.http.*

interface DetailedPhotoApi {
    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String
    ): DetailedPhotoInfo
    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String)
}