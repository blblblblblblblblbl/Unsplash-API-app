package com.blblblbl.profile.domain.model.user_info

import com.google.gson.annotations.SerializedName


data class Links (

  @SerializedName("self"      ) var self      : String? = null,
  @SerializedName("html"      ) var html      : String? = null,
  @SerializedName("photos"    ) var photos    : String? = null,
  @SerializedName("likes"     ) var likes     : String? = null,
  @SerializedName("portfolio" ) var portfolio : String? = null

)