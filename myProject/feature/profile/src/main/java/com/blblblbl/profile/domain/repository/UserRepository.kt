package com.blblblbl.profile.domain.repository

import androidx.paging.PagingData
import com.blblblbl.profile.domain.model.photo.Photo
import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.domain.model.user_info.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getMeInfo(): UserInfo?
    suspend fun getPublicUserInfo(username:String): PublicUserInfo
    fun  getLikedPhotosPagingDataFlow(username: String,pageSize:Int): Flow<PagingData<Photo>>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
    suspend fun clearStorage()
}