package com.blblblbl.detailedphoto.domain.model.photo_detailed



data class User(

    var id: String? = null,
    var updatedAt: String? = null,
    var username: String? = null,
    var name: String? = null,
    var portfolioUrl: String? = null,
    var bio: String? = null,
    var location: String? = null,
    var totalLikes: Int? = null,
    var totalPhotos: Int? = null,
    var totalCollections: Int? = null,
    var profileImage: ProfileImage? = ProfileImage(),
    var links: Links? = Links()

)