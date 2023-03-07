package com.blblblbl.profile.data.di

import com.blblblbl.profile.data.datasource.UserDataSource
import com.blblblbl.profile.data.datasource.UserDataSourceImpl
import com.blblblbl.profile.data.persistent_storage.PersistentStorage
import com.blblblbl.profile.data.persistent_storage.PersistentStorageImpl
import com.blblblbl.profile.data.repository.UserRepositoryImpl
import com.blblblbl.profile.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindUserDataSource(userDataSource: UserDataSourceImpl): UserDataSource
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: PersistentStorageImpl): PersistentStorage

}