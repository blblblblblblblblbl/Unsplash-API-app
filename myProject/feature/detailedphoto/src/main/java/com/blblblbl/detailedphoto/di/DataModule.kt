package com.blblblbl.detailedphoto.di

import com.blblblbl.detailedphoto.data.repository.DetailedPhotoRepositoryImpl
import com.blblblbl.detailedphoto.data.repository.DownloadNotifications
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import com.blblblbl.detailedphoto.ui.DownloadNotificationsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    @Binds
    abstract fun bindRepository(repositoryImpl: DetailedPhotoRepositoryImpl): DetailedPhotoRepository
    @Binds
    abstract fun bindNotify(DownloadNotificationsImpl: DownloadNotificationsImpl): DownloadNotifications
}