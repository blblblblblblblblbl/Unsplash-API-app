package com.blblblbl.search.domain.model.search

import com.blblblbl.search.domain.model.photo.Photo

data class SearchResult(
    var total: Int? = null,
    var totalPages: Int? = null,
    var results: ArrayList<Photo> = arrayListOf()
)