package com.blblblbl.mainfeed.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blblblbl.mainfeed.data.model.photos.Photo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class DBPhoto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id :String,
    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    val photo: Photo
)