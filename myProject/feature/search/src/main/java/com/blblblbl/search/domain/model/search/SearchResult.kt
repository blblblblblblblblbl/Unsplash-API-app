package com.blblblbl.search.domain.model.search

import com.blblblbl.search.domain.model.photo.Photo
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Photo> = arrayListOf()
)