package com.blblblbl.myapplication.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blblblbl.myapplication.data.repository.database.daos.PhotoDao
import com.blblblbl.myapplication.data.repository.database.daos.UnsplashRemoteKeysDao
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.data.repository.database.entities.UnsplashRemoteKeys
import com.blblblbl.myapplication.data.repository.database.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [DBPhoto::class, UnsplashRemoteKeys::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}