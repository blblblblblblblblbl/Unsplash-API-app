package com.blblblbl.profile.domain.model.user_info




data class UserInfo(

    var id: String? = null,
    var updatedAt: String? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var twitterUsername: String? = null,
    var portfolioUrl: String? = null,
    var bio: String? = null,
    var location: String? = null,
    var totalLikes: Int? = null,
    var totalPhotos: Int? = null,
    var totalCollections: Int? = null,
    var followedByUser: Boolean? = null,
    var downloads: Int? = null,
    var uploadsRemaining: Int? = null,
    var instagramUsername: String? = null,
    var email: String? = null,
    var links: Links? = Links()

)