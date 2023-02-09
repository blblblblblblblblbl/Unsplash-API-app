package com.blblblbl.myapplication.data.data_classes.photos

import com.google.gson.annotations.SerializedName


data class Links (

  @SerializedName("self"              ) var self             : String? = null,
  @SerializedName("html"              ) var html             : String? = null,
  @SerializedName("download"          ) var download         : String? = null,
  @SerializedName("download_location" ) var downloadLocation : String? = null

)