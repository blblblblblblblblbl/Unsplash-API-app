package com.blblblbl.profile.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.blblblbl.profile.data.model.user_info.UserInfo
import com.blblblbl.profile.data.persistent_storage.PersistentStorage.Companion.USER_INFO
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorageImpl @Inject constructor(
    private val storageConverter: StorageConverter,
    private val sharedPreferences: SharedPreferences
) : PersistentStorage {


    private val editor = sharedPreferences.edit()


    override fun addProperty(name: String?, value: String?) {

        Log.d("MyLog", "addProperty:$name $value")
        editor.putString(name, value)
        editor.apply()
    }

    override fun addUserInfo(name: String?, userInfo: UserInfo?) {

        val value = userInfo?.let {
            storageConverter.userInfoToJson(it)
        }
        editor.putString(name, value)
        editor.apply()
    }

    override fun clear() {
        editor.clear()
        editor.apply()
    }

    override fun getProperty(name: String?): String? {
        return sharedPreferences.getString(name, null)
    }

    override fun getUserInfo(): UserInfo? {
        val json = sharedPreferences.getString(USER_INFO, null)
        val userInfo = storageConverter.userInfoFromJson(json ?: "")
        return userInfo
    }

}