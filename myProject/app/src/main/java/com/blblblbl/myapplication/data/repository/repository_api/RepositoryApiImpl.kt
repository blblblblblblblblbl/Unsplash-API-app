package com.blblblbl.myapplication.data.repository.repository_api

import android.util.Log
import com.blblblbl.myapplication.MyApp
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.data_classes.search.SearchResult
import com.blblblbl.myapplication.data.repository.repository_api.utils.MockRequestInterceptor
import com.example.example.PhotoCollection
import com.example.example.UserInfo
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryApiImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
):RepositoryApi {

    object RetrofitServices {
        private const val BASE_URL = "https://api.unsplash.com/"
        private val gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .addInterceptor(MockRequestInterceptor(MyApp.appContext))
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

    override suspend fun getPhotosPage(page: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return RetrofitServices.photosApi.getPhotos(page, 10, "Bearer " + token)
    }

    override suspend fun getPhotosPage(page: Int, perPage: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return RetrofitServices.photosApi.getPhotos(page, perPage, "Bearer " + token)
    }

    override suspend fun searchPhotos(page: Int, perPage: Int, query: String): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        val response =
            RetrofitServices.photosApi.searchPhotos(page, perPage, query, "Bearer " + token)
        Log.d("MyLog", "search response: $response")
        return response.results
    }

    override suspend fun getCollectionPage(page: Int): List<PhotoCollection> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "collections page: " + page + "token: " + token)
        return RetrofitServices.collectionsApi.getCollection(page, 10, "Bearer " + token)
    }

    override suspend fun getUserInfo(): UserInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return RetrofitServices.userApi.getMe("Bearer " + token)
    }

    override suspend fun getPublicUserInfo(username: String): PublicUserInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return RetrofitServices.userApi.getPublicUserInfo(username, "Bearer " + token)
    }

    override suspend fun getLikedPhotosPage(page: Int, username: String): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return RetrofitServices.photosApi.getLikedPhotos(username, page, 10, "Bearer " + token)
    }

    override suspend fun getPhotoById(id: String): DetailedPhotoInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return RetrofitServices.photosApi.getPhotoById(id, "Bearer " + token)
    }

    override suspend fun getCollectionPhotoList(id: String, page: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return RetrofitServices.photosApi.getCollectionPhotos(id, page, 10, "Bearer " + token)
    }

    override suspend fun like(id: String) {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        RetrofitServices.likeApi.like(id, "Bearer " + token)
    }

    override suspend fun unlike(id: String) {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        RetrofitServices.likeApi.unlike(id, "Bearer " + token)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryApiModule{
    @Binds
    abstract fun bindRepositoryApi(repositoryApiImpl: RepositoryApiImpl):RepositoryApi
}