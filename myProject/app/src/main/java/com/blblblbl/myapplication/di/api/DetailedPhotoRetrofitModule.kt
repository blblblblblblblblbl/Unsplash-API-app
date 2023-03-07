package com.blblblbl.myapplication.di.api

import com.blblblbl.detailedphoto.data.network.DetailedPhotoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DetailedPhotoRetrofitModule {
    @Provides
    fun provideDetailedPhotoApi(retrofitCreator: RetrofitCreator): DetailedPhotoApi =
        retrofitCreator.createRetrofit().create(DetailedPhotoApi::class.java)
}