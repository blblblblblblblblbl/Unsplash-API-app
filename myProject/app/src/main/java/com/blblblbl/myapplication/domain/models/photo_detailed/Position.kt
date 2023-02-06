package com.blblblbl.myapplication.domain.models.photo_detailed

import com.google.gson.annotations.SerializedName


data class Position (

  @SerializedName("latitude"  ) var latitude  : Double? = null,
  @SerializedName("longitude" ) var longitude : Double? = null

)