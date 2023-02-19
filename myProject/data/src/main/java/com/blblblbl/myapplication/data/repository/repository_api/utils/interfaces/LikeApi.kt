package com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces

import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeApi {
    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String, @Header("Authorization") authHeader: String)

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String, @Header("Authorization") authHeader: String)
}