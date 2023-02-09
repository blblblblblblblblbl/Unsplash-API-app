package com.blblblbl.myapplication.domain.models.photo_detailed

import com.google.gson.annotations.SerializedName


data class DetailedPhotoInfo (

    @SerializedName("id"                       ) var id                     : String?                           = null,
    @SerializedName("created_at"               ) var createdAt              : String?                           = null,
    @SerializedName("updated_at"               ) var updatedAt              : String?                           = null,
    @SerializedName("width"                    ) var width                  : Int?                              = null,
    @SerializedName("height"                   ) var height                 : Int?                              = null,
    @SerializedName("color"                    ) var color                  : String?                           = null,
    @SerializedName("blur_hash"                ) var blurHash               : String?                           = null,
    @SerializedName("downloads"                ) var downloads              : Int?                              = null,
    @SerializedName("likes"                    ) var likes                  : Int?                              = null,
    @SerializedName("liked_by_user"            ) var likedByUser            : Boolean?                          = null,
    @SerializedName("public_domain"            ) var publicDomain           : Boolean?                          = null,
    @SerializedName("description"              ) var description            : String?                           = null,
    @SerializedName("exif"                     ) var exif                   : com.blblblbl.myapplication.domain.models.photo_detailed.Exif?                             = com.blblblbl.myapplication.domain.models.photo_detailed.Exif(),
    @SerializedName("location"                 ) var location               : com.blblblbl.myapplication.domain.models.photo_detailed.Location?                         = com.blblblbl.myapplication.domain.models.photo_detailed.Location(),
    @SerializedName("tags"                     ) var tags                   : ArrayList<com.blblblbl.myapplication.domain.models.photo_detailed.Tags>                   = arrayListOf(),
    @SerializedName("current_user_collections" ) var currentUserCollections : ArrayList<com.blblblbl.myapplication.domain.models.photo_detailed.CurrentUserCollections> = arrayListOf(),
    @SerializedName("urls"                     ) var urls                   : com.blblblbl.myapplication.domain.models.photo_detailed.Urls?                             = com.blblblbl.myapplication.domain.models.photo_detailed.Urls(),
    @SerializedName("links"                    ) var links                  : com.blblblbl.myapplication.domain.models.photo_detailed.Links?                            = com.blblblbl.myapplication.domain.models.photo_detailed.Links(),
    @SerializedName("user"                     ) var user                   : com.blblblbl.myapplication.domain.models.photo_detailed.User?                             = com.blblblbl.myapplication.domain.models.photo_detailed.User()

)