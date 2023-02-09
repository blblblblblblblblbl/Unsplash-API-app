package com.blblblbl.myapplication.data.data_classes.public_user_info

import com.google.gson.annotations.SerializedName


data class PublicUserInfoLinks (

  @SerializedName("self"      ) var self      : String? = null,
  @SerializedName("html"      ) var html      : String? = null,
  @SerializedName("photos"    ) var photos    : String? = null,
  @SerializedName("likes"     ) var likes     : String? = null,
  @SerializedName("portfolio" ) var portfolio : String? = null

)