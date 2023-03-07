package com.blblblbl.profile.domain.model.public_user_info

import com.google.gson.annotations.SerializedName


data class Social (

  @SerializedName("instagram_username" ) var instagramUsername : String? = null,
  @SerializedName("portfolio_url"      ) var portfolioUrl      : String? = null,
  @SerializedName("twitter_username"   ) var twitterUsername   : String? = null

)