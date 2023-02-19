package com.blblblbl.myapplication.data.repository.repository_api.utils

import android.content.Context
import com.blblblbl.myapplication.data.data_classes.collections.PhotoCollection
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.photos.Photo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.data_classes.search.SearchResult
import com.blblblbl.myapplication.data.data_classes.user_info.UserInfo
import com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces.CollectionsApi
import com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces.LikeApi
import com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces.PhotosApi
import com.blblblbl.myapplication.data.repository.repository_api.utils.interfaces.UserApi
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
) {
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


}