package com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces

import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.photos.Photo
import com.blblblbl.myapplication.data.data_classes.search.SearchResult
import retrofit2.http.*

interface PhotosApi {
    //@Headers("mock:true")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization") authHeader: String
    ): List<Photo>

    @Headers("mock:true")
    @GET("users/{username}/likes")
    suspend fun getLikedPhotos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization") authHeader: String
    ): List<Photo>

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): DetailedPhotoInfo
    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization") authHeader: String
    ): List<Photo>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("query") query: String,
        @Header("Authorization") authHeader: String
    ): SearchResult
}