package com.blblblbl.collections.domain.model.photo




data class CurrentUserCollections(

    var id: Int? = null,
    var title: String? = null,
    var publishedAt: String? = null,
    var lastCollectedAt: String? = null,
    var updatedAt: String? = null,
    var coverPhoto: String? = null,
    var user: String? = null

)