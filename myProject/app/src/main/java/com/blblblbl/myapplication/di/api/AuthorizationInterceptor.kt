package com.blblblbl.myapplication.di.api

import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorageImpl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val persistentStorage: PersistentStorageImpl
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + token)
            .build()
        return chain.proceed(request)
    }
}