package com.blblblbl.search.di

import com.blblblbl.search.data.repository.SearchRepositoryImpl
import com.blblblbl.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}