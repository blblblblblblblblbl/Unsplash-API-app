package com.blblblbl.mainfeed.data.database.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.blblblbl.mainfeed.data.model.photos.Photo

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