package com.blblblbl.myapplication.domain.models.collections


import com.google.gson.annotations.SerializedName


data class PhotoCollection (

    @SerializedName("id"                ) var id              : String?        = null,
    @SerializedName("title"             ) var title           : String?     = null,
    @SerializedName("description"       ) var description     : String?     = null,
    @SerializedName("published_at"      ) var publishedAt     : String?     = null,
    @SerializedName("last_collected_at" ) var lastCollectedAt : String?     = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?     = null,
    @SerializedName("total_photos"      ) var totalPhotos     : Int?        = null,
    @SerializedName("private"           ) var private         : Boolean?    = null,
    @SerializedName("share_key"         ) var shareKey        : String?     = null,
    @SerializedName("cover_photo"       ) var coverPhoto      : CoverPhoto? = CoverPhoto(),
    @SerializedName("user"              ) var user            : User?       = User(),
    @SerializedName("links"             ) var links           : Links?      = Links()

)