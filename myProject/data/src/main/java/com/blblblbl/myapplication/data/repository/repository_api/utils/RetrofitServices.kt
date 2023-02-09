package com.blblblbl.myapplication.data.repository.repository_api.utils

import android.content.Context
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.data_classes.user_info.UserInfo
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.domain.models.search.SearchResult
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitServices @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val BASE_URL = "https://api.unsplash.com/"
    private val gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(MockRequestInterceptor(context))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val photosApi: PhotosApi = retrofit.create(
        PhotosApi::class.java
    )
    val collectionsApi: CollectionsApi = retrofit.create(
        CollectionsApi::class.java
    )
    val userApi: UserApi = retrofit.create(
        UserApi::class.java
    )
    val likeApi: LikeApi = retrofit.create(
        LikeApi::class.java
    )

    interface PhotosApi {
        @Headers("mock:true")
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

    interface CollectionsApi {
        @Headers("mock:true")
        @GET("collections")
        suspend fun getCollection(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int = 10,
            @Header("Authorization") authHeader: String
        ): List<PhotoCollection>
    }

    interface UserApi {
        @GET("me")
        suspend fun getMe(@Header("Authorization") authHeader: String): UserInfo

        @GET("/users/{username}")
        suspend fun getPublicUserInfo(
            @Path("username") username: String,
            @Header("Authorization") authHeader: String
        ): PublicUserInfo
    }

    interface LikeApi {
        @POST("photos/{id}/like")
        suspend fun like(@Path("id") id: String, @Header("Authorization") authHeader: String)

        @DELETE("photos/{id}/like")
        suspend fun unlike(@Path("id") id: String, @Header("Authorization") authHeader: String)
    }
}