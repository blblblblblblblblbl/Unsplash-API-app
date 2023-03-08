package com.blblblbl.profile.data.persistent_storage.utils


import com.blblblbl.profile.data.model.user_info.UserInfo
import com.google.gson.GsonBuilder
import javax.inject.Inject

class StorageConverterImpl @Inject constructor(
    private val jsonParser: JsonParser
) : StorageConverter {
    override fun userInfoToJson(userInfo: UserInfo): String? {
        return jsonParser.toJson(
            userInfo,
            UserInfo::class.java
        )
    }

    override fun userInfoFromJson(json: String): UserInfo? {
        return jsonParser.fromJson<UserInfo>(
            json,
            UserInfo::class.java
        )
    }
}