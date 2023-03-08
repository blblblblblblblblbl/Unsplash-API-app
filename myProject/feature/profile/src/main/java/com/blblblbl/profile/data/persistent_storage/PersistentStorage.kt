package com.blblblbl.profile.data.persistent_storage

import com.blblblbl.profile.data.model.user_info.UserInfo


interface PersistentStorage {

    fun addProperty(name: String?, value: String?)

    fun addUserInfo(name: String?, userInfo: UserInfo?)

    fun clear()

    fun getProperty(name: String?): String?

    fun getUserInfo(): UserInfo?

    companion object {
        const val STORAGE_NAME = "StorageName"
        const val AUTH_TOKEN = "lastsearch"
        const val USER_INFO = "userinfo"
    }
}