package com.blblblbl.auth.di

import com.blblblbl.auth.data.repository.AuthRepositoryImpl
import com.blblblbl.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}