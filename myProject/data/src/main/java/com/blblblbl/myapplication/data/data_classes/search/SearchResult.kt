package com.blblblbl.myapplication.data.data_classes.search

import com.blblblbl.myapplication.data.data_classes.photos.Photo
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Photo> = arrayListOf()
)