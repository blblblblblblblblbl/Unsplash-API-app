package com.blblblbl.myapplication.di

import com.blblblbl.myapplication.data.DownloadNotifications
import com.blblblbl.myapplication.data.repository.RepositoryImpl
import com.blblblbl.myapplication.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
    @Binds
    abstract fun bindNotify(DownloadNotificationsImpl: DownloadNotificationsImpl): DownloadNotifications
}
