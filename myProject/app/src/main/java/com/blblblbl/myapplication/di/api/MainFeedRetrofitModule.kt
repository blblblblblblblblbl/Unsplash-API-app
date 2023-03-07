package com.blblblbl.myapplication.di.api

import com.blblblbl.mainfeed.data.network.MainFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class MainFeedRetrofitModule{

    @Provides
    fun provideMainFeedApi(retrofitCreator: RetrofitCreator): MainFeedApi =
        retrofitCreator.createRetrofit().create(MainFeedApi::class.java)
}