package com.blblblbl.myapplication.data.repository.repository_api

import android.util.Log
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.search.SearchResult
import com.blblblbl.myapplication.data.repository.repository_api.utils.MockRequestInterceptor
import com.blblblbl.myapplication.data.repository.repository_api.utils.RetrofitServices
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
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
    private val persistentStorage: PersistentStorage,
    private val retrofitServices: RetrofitServices
):RepositoryApi {

    override suspend fun getPhotosPage(page: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return retrofitServices.photosApi.getPhotos(page, 10, "Bearer " + token)
    }

    override suspend fun getPhotosPage(page: Int, perPage: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return retrofitServices.photosApi.getPhotos(page, perPage, "Bearer " + token)
    }

    override suspend fun searchPhotos(page: Int, perPage: Int, query: String): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        val response =
            retrofitServices.photosApi.searchPhotos(page, perPage, query, "Bearer " + token)
        Log.d("MyLog", "search response: $response")
        return response.results
    }

    override suspend fun getCollectionPage(page: Int): List<PhotoCollection> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "collections page: " + page + "token: " + token)
        return retrofitServices.collectionsApi.getCollection(page, 10, "Bearer " + token)
    }

    override suspend fun getUserInfo(): UserInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return retrofitServices.userApi.getMe("Bearer " + token)
    }

    override suspend fun getPublicUserInfo(username: String): com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return retrofitServices.userApi.getPublicUserInfo(username, "Bearer " + token)
    }

    override suspend fun getLikedPhotosPage(page: Int, username: String): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        Log.d("MyLog", "photos page: " + page + "token: " + token)
        return retrofitServices.photosApi.getLikedPhotos(username, page, 10, "Bearer " + token)
    }

    override suspend fun getPhotoById(id: String): com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return retrofitServices.photosApi.getPhotoById(id, "Bearer " + token)
    }

    override suspend fun getCollectionPhotoList(id: String, page: Int): List<Photo> {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        return retrofitServices.photosApi.getCollectionPhotos(id, page, 10, "Bearer " + token)
    }

    override suspend fun like(id: String) {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        retrofitServices.likeApi.like(id, "Bearer " + token)
    }

    override suspend fun unlike(id: String) {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        retrofitServices.likeApi.unlike(id, "Bearer " + token)
    }
}
