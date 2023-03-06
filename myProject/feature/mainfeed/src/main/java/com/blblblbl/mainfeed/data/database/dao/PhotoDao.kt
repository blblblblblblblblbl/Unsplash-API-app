package com.blblblbl.mainfeed.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blblblbl.mainfeed.data.database.entity.DBPhoto
import com.blblblbl.mainfeed.data.model.photos.Photo
@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAll(): List<DBPhoto>

    @Query("SELECT photo FROM photos")
    fun getAllPhotos(): List<Photo>

    @Query("SELECT photo FROM photos LIMIT :limit OFFSET :offset")
    fun getRange(offset:Int,limit:Int):List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbPhoto: DBPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(dbPhoto: List<DBPhoto>)

    @Query("SELECT photo FROM photos")
    fun getPhotosPagingSource(): PagingSource<Int, Photo>

    @Query("DELETE FROM photos")
    suspend fun clear()
}