package com.blblblbl.myapplication.data.repository.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blblblbl.myapplication.data.repository.database.entities.UnsplashRemoteKeys

@Dao
interface UnsplashRemoteKeysDao {

    @Query("SELECT * FROM unsplashRemoteKeysTable WHERE id =:id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM unsplashRemoteKeysTable")
    suspend fun deleteAllRemoteKeys()

}