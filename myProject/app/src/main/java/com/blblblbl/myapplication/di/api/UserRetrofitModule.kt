package com.blblblbl.myapplication.di.api

import com.blblblbl.profile.data.network.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UserRetrofitModule{

    @Provides
    fun provideUserApi(retrofitCreator: RetrofitCreator): UserApi=
        retrofitCreator.createRetrofit().create(UserApi::class.java)
}