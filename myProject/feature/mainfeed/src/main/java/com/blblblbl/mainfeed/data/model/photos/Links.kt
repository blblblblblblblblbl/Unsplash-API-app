package com.blblblbl.mainfeed.data.model.photos

import com.google.gson.annotations.SerializedName


data class Links(

    @SerializedName("self") var self: String? = null,
    @SerializedName("html") var html: String? = null,
    @SerializedName("download") var download: String? = null,
    @SerializedName("download_location") var downloadLocation: String? = null

)