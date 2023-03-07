package com.blblblbl.collections.domain.model.collection





data class PhotoCollection(

    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var publishedAt: String? = null,
    var lastCollectedAt: String? = null,
    var updatedAt: String? = null,
    var totalPhotos: Int? = null,
    var private: Boolean? = null,
    var shareKey: String? = null,
    var coverPhoto: CoverPhoto? = CoverPhoto(),
    var user: User? = User(),
    var links: Links? = Links()

)