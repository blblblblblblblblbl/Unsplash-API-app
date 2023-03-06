package com.blblblbl.collections.di

import com.blblblbl.collections.data.repository.CollectionRepositoryImpl
import com.blblblbl.collections.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{
    @Binds
    abstract fun bindRepository(repositoryImpl: CollectionRepositoryImpl): CollectionRepository
}