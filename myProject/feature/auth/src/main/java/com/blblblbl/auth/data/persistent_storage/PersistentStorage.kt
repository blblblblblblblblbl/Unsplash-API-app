package com.blblblbl.auth.data.persistent_storage




interface PersistentStorage {

    fun addProperty(name: String?, value: String?)
    fun clear()
    fun getProperty(name: String?): String?
    companion object{
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }
}