package com.blblblbl.myapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.data.repository.paging_sources.SearchPagingSource
import com.example.example.PhotoCollection
import com.example.example.UserInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPhotos(page: Int):List<Photo>
    fun getAllImages(): Flow<PagingData<DBPhoto>>
    fun searchImages(query: String): Flow<PagingData<Photo>>
    suspend fun getLikedPhotos(page: Int,userName:String):List<Photo>
    suspend fun getCollections(page: Int):List<PhotoCollection>
    suspend fun getUserInfo(): UserInfo?
    suspend fun getPublicUserInfo(username:String): PublicUserInfo
    suspend fun getPhotoById(id: String): DetailedPhotoInfo
    suspend fun getCollectionPhotoList(id:String,page: Int):List<Photo>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
    suspend fun clearDB()
}