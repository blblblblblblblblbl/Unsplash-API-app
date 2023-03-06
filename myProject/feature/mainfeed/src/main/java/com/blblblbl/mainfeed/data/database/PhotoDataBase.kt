package com.blblblbl.mainfeed.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blblblbl.mainfeed.data.database.dao.PhotoDao
import com.blblblbl.mainfeed.data.database.dao.UnsplashRemoteKeysDao
import com.blblblbl.mainfeed.data.database.entity.DBPhoto
import com.blblblbl.mainfeed.data.database.entity.UnsplashRemoteKeys
import com.blblblbl.mainfeed.data.database.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [DBPhoto::class, UnsplashRemoteKeys::class], version = 1)
abstract class PhotoDataBase:RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}