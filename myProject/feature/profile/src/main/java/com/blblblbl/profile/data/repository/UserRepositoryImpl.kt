package com.blblblbl.profile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.blblblbl.profile.data.datasource.UserDataSource
import com.blblblbl.profile.data.persistent_storage.PersistentStorage
import com.blblblbl.profile.data.persistent_storage.utils.StorageConverter
import com.blblblbl.profile.data.utils.mapToDomain
import com.blblblbl.profile.domain.model.photo.Photo
import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.domain.model.user_info.UserInfo

import com.blblblbl.profile.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val persistentStorage: PersistentStorage,
    private val likedPhotosPagingSource: LikedPhotosPagingSource
) : UserRepository {
    override suspend fun getMeInfo(): UserInfo? {
        try {
            val userInfo = userDataSource.getUserInfo()
            persistentStorage.addUserInfo(PersistentStorage.USER_INFO, userInfo)
            return userInfo.mapToDomain()
        } catch (e: Throwable) {
            val userInfo = persistentStorage.getUserInfo()
            return userInfo.mapToDomain()
        }
    }

    override suspend fun getPublicUserInfo(username: String): PublicUserInfo {
        return userDataSource.getPublicUserInfo(username).mapToDomain() ?: PublicUserInfo()
    }

    override fun getLikedPhotosPagingDataFlow(
        username: String,
        pageSize: Int
    ): Flow<PagingData<Photo>> {
        likedPhotosPagingSource.userNameinit(username)
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { likedPhotosPagingSource }
        ).flow.map { pagingData ->
            pagingData.map { photo ->
                photo.mapToDomain() ?: Photo()
            }
        }
    }

    override suspend fun like(id: String) {
        userDataSource.like(id)
    }

    override suspend fun unlike(id: String) {
        userDataSource.unlike(id)
    }

    override suspend fun clearStorage() {
        persistentStorage.clear()
        // TODO: clear database
        //repositoryDataBase.db.photoDao().clear()
    }
}