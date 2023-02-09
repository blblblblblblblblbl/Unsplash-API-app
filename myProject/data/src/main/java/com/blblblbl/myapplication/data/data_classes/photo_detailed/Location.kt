package com.blblblbl.myapplication.data.data_classes.photo_detailed

import com.google.gson.annotations.SerializedName


data class Location (

  @SerializedName("city"     ) var city     : String?   = null,
  @SerializedName("country"  ) var country  : String?   = null,
  @SerializedName("position" ) var position : Position? = Position()

)