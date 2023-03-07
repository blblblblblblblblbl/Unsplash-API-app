package com.blblblbl.mainfeed.data.model.photos


import com.google.gson.annotations.SerializedName


data class User(

    @SerializedName("id") var id: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("portfolio_url") var portfolioUrl: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("total_likes") var totalLikes: Int? = null,
    @SerializedName("total_photos") var totalPhotos: Int? = null,
    @SerializedName("total_collections") var totalCollections: Int? = null,
    @SerializedName("instagram_username") var instagramUsername: String? = null,
    @SerializedName("twitter_username") var twitterUsername: String? = null,
    @SerializedName("profile_image") var profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("links") var links: Links? = Links()

)