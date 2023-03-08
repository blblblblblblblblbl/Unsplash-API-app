package com.blblblbl.profile.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.blblblbl.profile.data.model.user_info.UserInfo
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorageImpl @Inject constructor(
    private @ApplicationContext var context: Context,
    private val storageConverter: StorageConverter
) : PersistentStorage {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private fun init() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    override fun addProperty(name: String?, value: String?) {
        if (sharedPreferences == null) {
            init()
        }
        Log.d("MyLog", "addProperty:$name $value")
        editor!!.putString(name, value)
        editor!!.apply()
    }

    override fun addUserInfo(name: String?, userInfo: UserInfo?) {
        if (sharedPreferences == null) {
            init()
        }
        val value = userInfo?.let {
            storageConverter.userInfoToJson(it)
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }

    override fun clear() {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.clear()
        editor!!.apply()
    }

    override fun getProperty(name: String?): String? {
        if (sharedPreferences == null) {
            init()
        }
        return sharedPreferences!!.getString(name, null)
    }

    override fun getUserInfo(): UserInfo? {
        if (sharedPreferences == null) {
            init()
        }
        val json = sharedPreferences!!.getString(STORAGE_NAME, null)
        val userInfo = storageConverter.userInfoFromJson(json ?: "")
        return userInfo
    }

    companion object {
        const val STORAGE_NAME = "StorageName"
        const val AUTH_TOKEN = "lastsearch"
        const val USER_INFO = "userinfo"
    }
}