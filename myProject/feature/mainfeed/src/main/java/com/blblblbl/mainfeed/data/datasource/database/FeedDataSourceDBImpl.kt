package com.blblblbl.mainfeed.data.datasource.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import com.blblblbl.mainfeed.data.database.PhotoDataBase
import com.blblblbl.mainfeed.data.database.utils.Converters
import com.blblblbl.mainfeed.data.database.utils.GsonParser
import com.blblblbl.mainfeed.data.model.photos.Photo
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FeedDataSourceDBImpl@Inject constructor(
    @ApplicationContext appContext: Context
):FeedDataSourceDB {
    private val gson = GsonBuilder().setLenient().create()
    lateinit var db: PhotoDataBase
    init {
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db"
        ).addTypeConverter(Converters(GsonParser(gson))).allowMainThreadQueries().build()
    }

    override fun getPhotosPagingSource(): PagingSource<Int, Photo> =
        db.photoDao().getPhotosPagingSource()

    override fun getDB(): PhotoDataBase = db
}