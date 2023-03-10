package com.blblblbl.collections.domain.model.collection


data class CoverPhoto(

    var id: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var color: String? = null,
    var blurHash: String? = null,
    var likes: Int? = null,
    var likedByUser: Boolean? = null,
    var description: String? = null,
    var user: User? = User(),
    var urls: Urls? = Urls(),
    var links: Links? = Links()

)