package com.blblblbl.myapplication.data.repository.di

import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryApiModule{
    @Binds
    abstract fun bindRepositoryApi(repositoryApiImpl: RepositoryApiImpl): RepositoryApi
}