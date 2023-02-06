package com.blblblbl.myapplication.data.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
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