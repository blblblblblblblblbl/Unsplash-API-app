package com.blblblbl.detailedphoto.domain.model.photo_detailed

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
    @SerializedName("exif"                     ) var exif                   : Exif?                             = Exif(),
    @SerializedName("location"                 ) var location               : Location?                         = Location(),
    @SerializedName("tags"                     ) var tags                   : ArrayList<Tags>                   = arrayListOf(),
    @SerializedName("current_user_collections" ) var currentUserCollections : ArrayList<CurrentUserCollections> = arrayListOf(),
    @SerializedName("urls"                     ) var urls                   : Urls?                             = Urls(),
    @SerializedName("links"                    ) var links                  : Links?                            = Links(),
    @SerializedName("user"                     ) var user                   : User?                             = User()

)