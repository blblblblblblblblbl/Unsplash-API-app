package com.blblblbl.profile.data.persistent_storage.di

import com.blblblbl.profile.data.persistent_storage.utils.GsonParser
import com.blblblbl.profile.data.persistent_storage.utils.JsonParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class StorageConverterModule {

    @Provides
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    fun provideJsonParser(gson:Gson): JsonParser = GsonParser(gson)

}