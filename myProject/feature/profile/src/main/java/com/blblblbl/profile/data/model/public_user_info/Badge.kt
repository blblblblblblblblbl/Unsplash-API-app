package com.blblblbl.profile.data.model.public_user_info

import com.google.gson.annotations.SerializedName


data class Badge (

  @SerializedName("title"   ) var title   : String?  = null,
  @SerializedName("primary" ) var primary : Boolean? = null,
  @SerializedName("slug"    ) var slug    : String?  = null,
  @SerializedName("link"    ) var link    : String?  = null

)