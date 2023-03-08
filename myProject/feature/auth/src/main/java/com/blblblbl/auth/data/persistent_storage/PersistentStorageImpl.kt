package com.blblblbl.auth.data.persistent_storage

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class PersistentStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
):PersistentStorage {

    private val editor = sharedPreferences.edit()

    override fun addProperty(name: String?, value: String?) {

        Log.d("MyLog", "addProperty:$name $value")
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

}