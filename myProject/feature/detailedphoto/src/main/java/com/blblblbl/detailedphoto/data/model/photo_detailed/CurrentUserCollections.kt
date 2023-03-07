package com.blblblbl.detailedphoto.data.model.photo_detailed

import com.google.gson.annotations.SerializedName


data class CurrentUserCollections(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("last_collected_at") var lastCollectedAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("cover_photo") var coverPhoto: String? = null,
    @SerializedName("user") var user: String? = null

)