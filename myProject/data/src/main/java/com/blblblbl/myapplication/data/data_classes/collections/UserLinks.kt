package com.blblblbl.myapplication.data.data_classes.collections

import com.google.gson.annotations.SerializedName


data class UserLinks (

  @SerializedName("self"    ) var self    : String? = null,
  @SerializedName("html"    ) var html    : String? = null,
  @SerializedName("photos"  ) var photos  : String? = null,
  @SerializedName("related" ) var related : String? = null

)