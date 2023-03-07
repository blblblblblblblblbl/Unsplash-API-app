package com.blblblbl.mainfeed.data.di

import com.blblblbl.mainfeed.data.datasource.database.FeedDataSourceDB
import com.blblblbl.mainfeed.data.datasource.database.FeedDataSourceDBImpl
import com.blblblbl.mainfeed.data.datasource.network.FeedDataSourceNet
import com.blblblbl.mainfeed.data.datasource.network.FeedDataSourceNetImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindFeedDataSourceNet(dataSourceNet: FeedDataSourceNetImpl): FeedDataSourceNet
    @Binds
    abstract fun bindFeedDataSourceDB(dataSourceDB: FeedDataSourceDBImpl): FeedDataSourceDB

}