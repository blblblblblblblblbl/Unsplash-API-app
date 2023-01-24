package com.blblblbl.myapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.example.example.PhotoCollection
import com.example.example.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val repositoryApi: RepositoryApi,
    private val repositoryDataBase: RepositoryDataBase,
    private val persistentStorage: PersistentStorage
) {

   suspend fun getPhotos(page: Int):List<Photo>{
       val listApi = repositoryApi.getPhotosPage(page)
       var listDB = mutableListOf<DBPhoto>()
       listApi.forEach{ photo->
           photo.id?.let {id->
               repositoryDataBase.db.photoDao().insert(DBPhoto(id,photo))
           }
       }
       return listApi
   }
    @OptIn(ExperimentalPagingApi::class)
    fun getAllImages(): Flow<PagingData<DBPhoto>> {
        val pagingSourceFactory = { repositoryDataBase.db.photoDao().getPhotosPagingSource()}
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                repositoryApi = repositoryApi,
                repositoryDataBase = repositoryDataBase

            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    fun searchImages(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(repositoryApi = repositoryApi, query = query)
            }
        ).flow
    }
    suspend fun getLikedPhotos(page: Int,userName:String):List<Photo>{
        return repositoryApi.getLikedPhotosPage(page,userName)
    }
    suspend fun getCollections(page: Int):List<PhotoCollection>{
        return repositoryApi.getCollectionPage(page)
    }
    suspend fun getUserInfo():UserInfo?{
        try {
            val userInfo = repositoryApi.getUserInfo()
            persistentStorage.addProperty(PersistentStorage.USER_INFO,userInfo)
            return userInfo
        }
        catch (e:Throwable){
            val json = persistentStorage.getProperty(PersistentStorage.USER_INFO)
            val userInfo = StorageConverter.userInfoFromJson(json?:"")
            return userInfo
        }

    }
    suspend fun getPublicUserInfo(username:String): PublicUserInfo {
        return repositoryApi.getPublicUserInfo(username)
    }

    suspend fun getPhotoById(id: String): DetailedPhotoInfo {
        return repositoryApi.getPhotoById(id)
    }
    suspend fun getCollectionPhotoList(id:String,page: Int):List<Photo>{
        return repositoryApi.getCollectionPhotoList(id,page)
    }
    suspend fun like(id: String){
        repositoryApi.like(id)
    }
    suspend fun unlike(id: String){
        repositoryApi.unlike(id)
    }
    suspend fun clearDB(){
        repositoryDataBase.db.photoDao().clear()
    }
    companion object{
        const val ITEMS_PER_PAGE:Int = 10
    }
}