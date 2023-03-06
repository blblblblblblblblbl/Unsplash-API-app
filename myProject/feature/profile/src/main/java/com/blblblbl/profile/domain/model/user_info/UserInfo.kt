package com.blblblbl.profile.domain.model.user_info

import com.google.gson.annotations.SerializedName


data class UserInfo (

  @SerializedName("id"                 ) var id                : String?  = null,
  @SerializedName("updated_at"         ) var updatedAt         : String?  = null,
  @SerializedName("username"           ) var username          : String?  = null,
  @SerializedName("first_name"         ) var firstName         : String?  = null,
  @SerializedName("last_name"          ) var lastName          : String?  = null,
  @SerializedName("twitter_username"   ) var twitterUsername   : String?  = null,
  @SerializedName("portfolio_url"      ) var portfolioUrl      : String?  = null,
  @SerializedName("bio"                ) var bio               : String?  = null,
  @SerializedName("location"           ) var location          : String?  = null,
  @SerializedName("total_likes"        ) var totalLikes        : Int?     = null,
  @SerializedName("total_photos"       ) var totalPhotos       : Int?     = null,
  @SerializedName("total_collections"  ) var totalCollections  : Int?     = null,
  @SerializedName("followed_by_user"   ) var followedByUser    : Boolean? = null,
  @SerializedName("downloads"          ) var downloads         : Int?     = null,
  @SerializedName("uploads_remaining"  ) var uploadsRemaining  : Int?     = null,
  @SerializedName("instagram_username" ) var instagramUsername : String?  = null,
  @SerializedName("email"              ) var email             : String?  = null,
  @SerializedName("links"              ) var links             : Links?   = Links()

)