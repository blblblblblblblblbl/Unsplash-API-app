package com.blblblbl.search.data.model.search

import com.blblblbl.search.data.model.photo.Photo
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Photo> = arrayListOf()
)