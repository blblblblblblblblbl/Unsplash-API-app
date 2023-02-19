package com.blblblbl.myapplication.data.persistent_storage

import com.blblblbl.myapplication.data.data_classes.user_info.UserInfo

interface PersistentStorage {

    fun addProperty(name: String?, value: String?)
    fun addProperty(name: String?, userInfo: UserInfo?)
    fun clear()
    fun getProperty(name: String?): String?
    companion object{
        const val STORAGE_NAME = "StorageName"
        const val AUTH_TOKEN = "lastsearch"
        const val USER_INFO = "userinfo"
    }
}