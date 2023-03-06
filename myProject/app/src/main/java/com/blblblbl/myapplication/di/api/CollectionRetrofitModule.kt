package com.blblblbl.myapplication.di.api

import com.blblblbl.collections.data.network.CollectionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class CollectionRetrofitModule {
    @Provides
    fun provideCollectionApi(retrofitCreator: RetrofitCreator): CollectionApi =
        retrofitCreator.createRetrofit().create(CollectionApi::class.java)
}