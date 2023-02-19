package com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces

import com.blblblbl.myapplication.data.data_classes.collections.PhotoCollection
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface CollectionsApi {
    @Headers("mock:true")
    @GET("collections")
    suspend fun getCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Header("Authorization") authHeader: String
    ): List<PhotoCollection>
}