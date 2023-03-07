package com.blblblbl.collections.domain.model.collection




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
    var links: UserLinks? = UserLinks()

)