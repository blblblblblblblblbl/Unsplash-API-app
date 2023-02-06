package com.blblblbl.myapplication.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorage @Inject constructor(
    @ApplicationContext context: Context
) {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var context: Context = context

    private fun init() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    fun addProperty(name: String?, value: String?) {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }
    fun addProperty(name: String?, userInfo: UserInfo?) {
        if (sharedPreferences == null) {
            init()
        }
        val value = userInfo?.let {
            StorageConverter.userInfoToJson(it)
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }
    fun clear() {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.clear()
        editor!!.apply()
    }

    fun getProperty(name: String?): String? {
        if (sharedPreferences == null) {
            init()
        }
        return sharedPreferences!!.getString(name, null)
    }
    companion object{
        const val STORAGE_NAME = "StorageName"
        const val AUTH_TOKEN = "lastsearch"
        const val USER_INFO = "userinfo"
    }
}