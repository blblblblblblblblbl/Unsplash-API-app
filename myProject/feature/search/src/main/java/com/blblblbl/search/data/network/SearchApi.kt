package com.blblblbl.search.data.network

import com.blblblbl.search.data.model.search.SearchResult
import retrofit2.http.*

interface SearchApi {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("query") query: String
    ): SearchResult

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String)
}