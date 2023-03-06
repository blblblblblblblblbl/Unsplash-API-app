package com.blblblbl.search.data.di


import com.blblblbl.search.data.datasource.SearchDataSource
import com.blblblbl.search.data.datasource.SearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindUserDataSource(searchDataSource: SearchDataSourceImpl): SearchDataSource
}