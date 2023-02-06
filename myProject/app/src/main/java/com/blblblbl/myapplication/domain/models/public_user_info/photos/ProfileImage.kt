package com.blblblbl.myapplication.domain.models.public_user_info.photos

import com.google.gson.annotations.SerializedName


data class ProfileImage (

  @SerializedName("small"  ) var small  : String? = null,
  @SerializedName("medium" ) var medium : String? = null,
  @SerializedName("large"  ) var large  : String? = null

)