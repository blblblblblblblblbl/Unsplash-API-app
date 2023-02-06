package com.blblblbl.myapplication.domain.models.collections

import com.blblblbl.myapplication.domain.models.public_user_info.photos.Links
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Urls
import com.blblblbl.myapplication.domain.models.public_user_info.photos.User
import com.google.gson.annotations.SerializedName


data class CoverPhoto (

    @SerializedName("id"            ) var id          : String?  = null,
    @SerializedName("width"         ) var width       : Int?     = null,
    @SerializedName("height"        ) var height      : Int?     = null,
    @SerializedName("color"         ) var color       : String?  = null,
    @SerializedName("blur_hash"     ) var blurHash    : String?  = null,
    @SerializedName("likes"         ) var likes       : Int?     = null,
    @SerializedName("liked_by_user" ) var likedByUser : Boolean? = null,
    @SerializedName("description"   ) var description : String?  = null,
    @SerializedName("user"          ) var user        : User?    = User(),
    @SerializedName("urls"          ) var urls        : Urls?    = Urls(),
    @SerializedName("links"         ) var links       : Links?   = Links()

)