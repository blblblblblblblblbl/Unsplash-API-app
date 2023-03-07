package com.blblblbl.detailedphoto.domain.model.photo_detailed




data class DetailedPhotoInfo(

    var id: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var color: String? = null,
    var blurHash: String? = null,
    var downloads: Int? = null,
    var likes: Int? = null,
    var likedByUser: Boolean? = null,
    var publicDomain: Boolean? = null,
    var description: String? = null,
    var exif: Exif? = Exif(),
    var location: Location? = Location(),
    var tags: ArrayList<Tags> = arrayListOf(),
    var currentUserCollections: ArrayList<CurrentUserCollections> = arrayListOf(),
    var urls: Urls? = Urls(),
    var links: Links? = Links(),
    var user: User? = User()

)