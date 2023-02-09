package com.blblblbl.myapplication.data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.*
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.data.repository.paging_sources.SearchPagingSource
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_db.RepositoryDataBase
import com.blblblbl.myapplication.data.utils.mapToDomain
import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.openid.appauth.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repositoryApi: RepositoryApi,
    private val repositoryDataBase: RepositoryDataBase,
    private val persistentStorage: PersistentStorage
): Repository {
    override fun authorize(code: String) {
        var authService = AuthorizationService(context)
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://unsplash.com/oauth/authorize"),  // authorization endpoint
            Uri.parse("https://unsplash.com/oauth/token")) // token endpoint
        val clientAuth: ClientAuthentication = ClientSecretBasic(SECRET_KEY)
        authService.performTokenRequest(
            TokenRequest.Builder(serviceConfig, MY_CLIENT_ID)
                .setAuthorizationCode(code)
                .setRedirectUri(MY_REDIRECT_URI.toUri())
                .setGrantType("authorization_code")
                .build(),
            clientAuth,
            AuthorizationService.TokenResponseCallback { resp, ex ->
                if (resp != null) {
                    Log.d(
                        "MyLog",
                        "accessToken:" + resp.accessToken.toString() + "\ntokenType: " + resp.tokenType + "\nscope: " + resp.scopeSet
                    )
                    persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,resp.accessToken.toString())
                    // exchange succeeded
                } else {
                    Log.d("MyLog", ex.toString())
                    // authorization failed, check ex for more details
                }
            })
    }

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
    /*@OptIn(ExperimentalPagingApi::class)
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
    }*/
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
    override suspend fun getMeInfo(): UserInfo?{
        try {
            val userInfo = repositoryApi.getUserInfo()
            persistentStorage.addProperty(PersistentStorage.USER_INFO,userInfo)
            return userInfo.mapToDomain()
        }
        catch (e:Throwable){
            val json = persistentStorage.getProperty(PersistentStorage.USER_INFO)
            val userInfo = StorageConverter.userInfoFromJson(json?:"")
            return userInfo.mapToDomain()
        }

    }
    override suspend fun getPublicUserInfo(username:String): PublicUserInfo {
        return repositoryApi.getPublicUserInfo(username).mapToDomain()?: PublicUserInfo()
    }

    override suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo {
        return repositoryApi.getPhotoById(id).mapToDomain()?:DetailedPhotoInfo()
    }


    override fun downloadImg(detailedPhotoInfo: DetailedPhotoInfo){
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        Log.d("MyLog","downloadWorkRequest")
        val downloadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                Data.Builder()
                    .putString(DownloadWorker.URL,detailedPhotoInfo.urls?.raw)
                    .putString(DownloadWorker.ID,detailedPhotoInfo.id)
                    .build())
            .setConstraints(constraints)
            .build()
        Log.d("MyLog","WorkManager")
        WorkManager
            .getInstance(context)
            .enqueue(downloadWorkRequest)
        Log.d("MyLog","afterWorkManager")
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
        const val MY_CLIENT_ID:String ="RoIF7WHVqFj86IPcmNSz_dKxmUaDRGANTaSk9aqnyac"
        const val MY_REDIRECT_URI:String ="myproject://www.exagfdasrvxcmple.com/gizmos"
        const val SECRET_KEY:String ="e1guRuEqxqtvMOf9L3_Sf_S_z1P8cs41C720MpfdWqw"
    }
}

