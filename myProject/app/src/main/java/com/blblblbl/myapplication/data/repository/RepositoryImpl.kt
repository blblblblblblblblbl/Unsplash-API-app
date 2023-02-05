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
import com.blblblbl.myapplication.data.repository.paging_sources.SearchPagingSource
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_db.RepositoryDataBase
import com.blblblbl.myapplication.domain.repository.Repository
import com.example.example.PhotoCollection
import com.example.example.UserInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val repositoryApi: RepositoryApi,
    private val repositoryDataBase: RepositoryDataBase,
    private val persistentStorage: PersistentStorage
): Repository {

   override suspend fun getImgs(page: Int):List<Photo>{
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
    override fun getAllImgs(): Flow<PagingData<DBPhoto>> {
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
    override fun searchImgByTags(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(repositoryApi = repositoryApi, query = query)
            }
        ).flow
    }
    override suspend fun getLikedImgs(page: Int, userName:String):List<Photo>{
        return repositoryApi.getLikedPhotosPage(page,userName)
    }
    override suspend fun getCollections(page: Int):List<PhotoCollection>{
        return repositoryApi.getCollectionPage(page)
    }
    override suspend fun getMeInfo():UserInfo?{
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
    override suspend fun getPublicUserInfo(username:String): PublicUserInfo {
        return repositoryApi.getPublicUserInfo(username)
    }

    override suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo {
        return repositoryApi.getPhotoById(id)
    }
    override suspend fun getCollectionImgList(id:String, page: Int):List<Photo>{
        return repositoryApi.getCollectionPhotoList(id,page)
    }
    override suspend fun like(id: String){
        repositoryApi.like(id)
    }
    override suspend fun unlike(id: String){
        repositoryApi.unlike(id)
    }
    override suspend fun clearDB(){
        repositoryDataBase.db.photoDao().clear()
    }
    companion object{
        const val ITEMS_PER_PAGE:Int = 10
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}