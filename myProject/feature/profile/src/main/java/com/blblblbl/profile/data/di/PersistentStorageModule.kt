package com.blblblbl.profile.data.di


import com.blblblbl.profile.data.persistent_storage.utils.StorageConverter
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter


}