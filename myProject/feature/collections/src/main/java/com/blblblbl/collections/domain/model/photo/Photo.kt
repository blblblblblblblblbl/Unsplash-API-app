package com.blblblbl.collections.domain.model.photo



data class Photo(

    var id: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var color: String? = null,
    var blurHash: String? = null,
    var likes: Int? = null,
    var likedByUser: Boolean? = null,
    var description: String? = null,
    var user: User? = User(),
    var currentUserCollections: ArrayList<CurrentUserCollections> = arrayListOf(),
    var urls: Urls? = Urls(),
    var links: Links? = Links()

)