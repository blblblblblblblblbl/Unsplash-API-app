package com.blblblbl.collections.data.di

import com.blblblbl.collections.data.datasource.CollectionDataSource
import com.blblblbl.collections.data.datasource.CollectionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindDetailedPhotoDataSource(collectionDataSource: CollectionDataSourceImpl): CollectionDataSource
}