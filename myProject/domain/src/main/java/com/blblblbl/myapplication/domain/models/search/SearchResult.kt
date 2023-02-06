package com.blblblbl.myapplication.domain.models.search

import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Photo> = arrayListOf()
)