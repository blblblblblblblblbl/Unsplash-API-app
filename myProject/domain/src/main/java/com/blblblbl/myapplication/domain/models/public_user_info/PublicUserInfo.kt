package com.blblblbl.myapplication.domain.models.public_user_info

import com.google.gson.annotations.SerializedName


data class PublicUserInfo (

    @SerializedName("id"                 ) var id                : String?       = null,
    @SerializedName("updated_at"         ) var updatedAt         : String?       = null,
    @SerializedName("username"           ) var username          : String?       = null,
    @SerializedName("name"               ) var name              : String?       = null,
    @SerializedName("first_name"         ) var firstName         : String?       = null,
    @SerializedName("last_name"          ) var lastName          : String?       = null,
    @SerializedName("instagram_username" ) var instagramUsername : String?       = null,
    @SerializedName("twitter_username"   ) var twitterUsername   : String?       = null,
    @SerializedName("portfolio_url"      ) var portfolioUrl      : String?       = null,
    @SerializedName("bio"                ) var bio               : String?       = null,
    @SerializedName("location"           ) var location          : String?       = null,
    @SerializedName("total_likes"        ) var totalLikes        : Int?          = null,
    @SerializedName("total_photos"       ) var totalPhotos       : Int?          = null,
    @SerializedName("total_collections"  ) var totalCollections  : Int?          = null,
    @SerializedName("followed_by_user"   ) var followedByUser    : Boolean?      = null,
    @SerializedName("followers_count"    ) var followersCount    : Int?          = null,
    @SerializedName("following_count"    ) var followingCount    : Int?          = null,
    @SerializedName("downloads"          ) var downloads         : Int?          = null,
    @SerializedName("social"             ) var social            : Social?       = Social(),
    @SerializedName("profile_image"      ) var profileImage      : ProfileImage? = ProfileImage(),
    @SerializedName("badge"              ) var badge             : Badge?        = Badge(),
    @SerializedName("links"              ) var links             : PublicUserInfoLinks?    = PublicUserInfoLinks()

)