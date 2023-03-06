package com.blblblbl.detailedphoto.data.model.photo_detailed

import com.google.gson.annotations.SerializedName


data class Position (

  @SerializedName("latitude"  ) var latitude  : Double? = null,
  @SerializedName("longitude" ) var longitude : Double? = null

)