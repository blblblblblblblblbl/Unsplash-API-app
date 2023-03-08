package com.blblblbl.myapplication.data.persistent_storage

import com.blblblbl.profile.data.model.user_info.UserInfo


interface PersistentStorage {

    fun addProperty(name: String?, value: String?)
    fun clear()
    fun getProperty(name: String?): String?
    companion object{
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }
}