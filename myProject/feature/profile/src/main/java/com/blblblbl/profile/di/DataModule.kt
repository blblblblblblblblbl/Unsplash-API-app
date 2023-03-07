package com.blblblbl.profile.di

import com.blblblbl.profile.data.repository.UserRepositoryImpl
import com.blblblbl.profile.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}