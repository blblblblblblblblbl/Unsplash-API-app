package com.blblblbl.myapplication.data.repository


import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
):Repository {
    override fun getSavedBearerToken(): String? {
        return persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
    }
}