package com.blblblbl.detailedphoto.data.di

import com.blblblbl.detailedphoto.data.datasource.DetailedPhotoDataSource
import com.blblblbl.detailedphoto.data.datasource.DetailedPhotoDataSourceImpl
import com.blblblbl.detailedphoto.data.repository.DetailedPhotoRepositoryImpl
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindDetailedPhotoDataSource(detailedPhotoDataSource: DetailedPhotoDataSourceImpl): DetailedPhotoDataSource
}