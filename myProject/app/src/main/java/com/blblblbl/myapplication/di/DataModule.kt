package com.blblblbl.myapplication.di

import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    @Binds
    abstract fun bindRepositoryApi(repositoryApiImpl: RepositoryApiImpl): RepositoryApi
}