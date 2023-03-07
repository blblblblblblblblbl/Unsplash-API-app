package com.blblblbl.profile.domain.model.public_user_info




data class PublicUserInfo(

    var id: String? = null,
    var updatedAt: String? = null,
    var username: String? = null,
    var name: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var instagramUsername: String? = null,
    var twitterUsername: String? = null,
    var portfolioUrl: String? = null,
    var bio: String? = null,
    var location: String? = null,
    var totalLikes: Int? = null,
    var totalPhotos: Int? = null,
    var totalCollections: Int? = null,
    var followedByUser: Boolean? = null,
    var followersCount: Int? = null,
    var followingCount: Int? = null,
    var downloads: Int? = null,
    var social: Social? = Social(),
    var profileImage: ProfileImage? = ProfileImage(),
    var badge: Badge? = Badge(),
    var links: PublicUserInfoLinks? = PublicUserInfoLinks()

)