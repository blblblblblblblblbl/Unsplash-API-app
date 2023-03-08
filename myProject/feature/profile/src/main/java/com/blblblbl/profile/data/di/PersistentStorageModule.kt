package com.blblblbl.profile.data.di


import android.content.Context
import android.content.SharedPreferences
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverter
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter




}
@Module
@InstallIn(SingletonComponent::class)
class PersistentStorageModuleNotAbstract(
){
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val STORAGE_NAME = "StorageName"
    }

}