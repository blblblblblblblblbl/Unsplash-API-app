package com.blblblbl.myapplication.data.repository.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsplashRemoteKeysTable")
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)