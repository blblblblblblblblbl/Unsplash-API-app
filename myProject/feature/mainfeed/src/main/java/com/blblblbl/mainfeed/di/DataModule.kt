package com.blblblbl.mainfeed.di


import com.blblblbl.mainfeed.data.repository.FeedRepositoryImpl
import com.blblblbl.mainfeed.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(feedRepository: FeedRepositoryImpl): FeedRepository
}