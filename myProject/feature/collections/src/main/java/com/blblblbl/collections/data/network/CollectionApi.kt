package com.blblblbl.collections.data.network

import com.blblblbl.collections.data.model.collection.PhotoCollection
import com.blblblbl.collections.data.model.photo.Photo
import retrofit2.http.*

interface CollectionApi {

    @Headers("mock:true")
    @GET("collections")
    suspend fun getCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<PhotoCollection>

    @Headers("mock:true")
    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<Photo>

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String)
}