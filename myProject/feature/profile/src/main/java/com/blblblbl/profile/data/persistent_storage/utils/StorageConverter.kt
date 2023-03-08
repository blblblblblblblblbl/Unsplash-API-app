package com.blblblbl.profile.data.persistent_storage.utils

import com.blblblbl.profile.data.model.user_info.UserInfo

interface StorageConverter {

    fun userInfoToJson(userInfo: UserInfo): String?

    fun userInfoFromJson(json: String): UserInfo?
}