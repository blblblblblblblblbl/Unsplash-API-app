package com.blblblbl.myapplication.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferencesMain

@Module
@InstallIn(SingletonComponent::class)
class PersistentStorageModule(
){
    @SharedPreferencesMain
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val STORAGE_NAME = "StorageName"
    }

}
