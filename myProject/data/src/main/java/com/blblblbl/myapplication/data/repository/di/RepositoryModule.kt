package com.blblblbl.myapplication.data.repository.di

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindRepositoryApi(repositoryApiImpl: RepositoryApiImpl): RepositoryApi
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: PersistentStorageImpl): PersistentStorage
}