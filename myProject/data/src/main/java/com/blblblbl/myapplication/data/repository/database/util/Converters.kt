package com.blblblbl.myapplication.data.repository.database.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.blblblbl.myapplication.domain.models.photos.Photo

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(photo: Photo) : String {
        return jsonParser.toJson(
            photo,
            Photo::class.java
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): Photo {
        return jsonParser.fromJson<Photo>(
            json,
            Photo::class.java
        ) ?: Photo()
    }
}