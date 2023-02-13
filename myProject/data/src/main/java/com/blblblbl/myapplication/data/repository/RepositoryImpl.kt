package com.blblblbl.myapplication.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.paging.*
import androidx.work.*
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.blblblbl.myapplication.data.repository.paging_sources.CollectionPhotoPagingSource
import com.blblblbl.myapplication.data.repository.paging_sources.CollectionsPagingSource
import com.blblblbl.myapplication.data.repository.paging_sources.LikedPhotosPagingSource
import com.blblblbl.myapplication.data.repository.paging_sources.SearchPagingSource
import com.blblblbl.myapplication.data.repository.repository_api.RepositoryApi
import com.blblblbl.myapplication.data.repository.repository_db.RepositoryDataBase
import com.blblblbl.myapplication.data.utils.mapToDomain
import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.openid.appauth.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repositoryApi: RepositoryApi,
    private val repositoryDataBase: RepositoryDataBase,
    private val persistentStorage: PersistentStorage,
    private val collectionPhotosPagingSource: CollectionPhotoPagingSource,
    private val collectionsPagingSource: CollectionsPagingSource,
    private val likedPhotosPagingSource: LikedPhotosPagingSource
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
                    persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,resp.accessToken.toString())
                    // exchange succeeded
                } else {
                    ex?.printStackTrace()
                    // authorization failed, check ex for more details
                }
            })
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllImgs(pageSize: Int): Flow<PagingData<Photo>> {
        val pagingSourceFactory = { repositoryDataBase.db.photoDao().getPhotosPagingSource()}
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = UnsplashRemoteMediator(
                repositoryApi = repositoryApi,
                repositoryDataBase = repositoryDataBase

            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { photo->photo.mapToDomain()?:Photo()
            }
        }
    }
    override fun searchImgByTags(query: String,pageSize: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                SearchPagingSource(repositoryApi = repositoryApi, query = query)
            }
        ).flow
    }
    override suspend fun getLikedImgs(page: Int, userName:String):List<Photo>{
        val temp = repositoryApi.getLikedPhotosPage(page,userName)
        val listPhotos = mutableListOf<Photo>()
        temp.forEach {
            it.mapToDomain()?.let { it1 -> listPhotos.add(it1) }
        }
        return listPhotos.toList()
    }
    override suspend fun getCollections(page: Int):List<PhotoCollection>{
        val temp = repositoryApi.getCollectionPage(page)
        val collections = mutableListOf<PhotoCollection>()
        temp.forEach {
            it.mapToDomain()?.let { it1 -> collections.add(it1) }
        }
        return collections.toList()
    }

    override fun getCollectionPhotosPagingDataFlow(
        id: String,
        pageSize: Int
    ): Flow<PagingData<Photo>> {
        collectionPhotosPagingSource.idInit(id)
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { collectionPhotosPagingSource }
        ).flow.map { pagingData->
            pagingData.map { it.mapToDomain()?:Photo() }
        }
    }

    override fun getCollectionPagingDataFlow(pageSize: Int): Flow<PagingData<PhotoCollection>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { collectionsPagingSource }
        ).flow.map { pagingData->
            pagingData.map { it.mapToDomain()?: PhotoCollection() }
        }
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

    override fun getLikedPhotosPagingDataFlow(
        username: String,
        pageSize: Int
    ): Flow<PagingData<Photo>> {
        likedPhotosPagingSource.userNameinit(username)
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { likedPhotosPagingSource }
        ).flow.map { pagingData ->
            pagingData.map { photo->photo.mapToDomain()?:Photo()
            }
        }
    }

    override fun getSavedBearerToken(): String? {
        return persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
    }

    override suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo {
        return repositoryApi.getPhotoById(id).mapToDomain()?:DetailedPhotoInfo()
    }


    override fun downloadImg(detailedPhotoInfo: DetailedPhotoInfo){
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val downloadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                Data.Builder()
                    .putString(DownloadWorker.URL,detailedPhotoInfo.urls?.raw)
                    .putString(DownloadWorker.ID,detailedPhotoInfo.id)
                    .build())
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(context)
            .enqueue(downloadWorkRequest)
    }

    override suspend fun getCollectionImgList(id:String, page: Int):List<Photo>{
        val temp = repositoryApi.getCollectionPhotoList(id,page)
        val listPhotos = mutableListOf<Photo>()
        temp.forEach {
            it.mapToDomain()?.let { it1 -> listPhotos.add(it1) }
        }
        return listPhotos.toList()
    }
    override suspend fun like(id: String){
        repositoryApi.like(id)
    }
    override suspend fun unlike(id: String){
        repositoryApi.unlike(id)
    }
    override suspend fun clearStorage(){
        persistentStorage.clear()
        repositoryDataBase.db.photoDao().clear()
    }
    companion object{
        const val MY_CLIENT_ID:String ="RoIF7WHVqFj86IPcmNSz_dKxmUaDRGANTaSk9aqnyac"
        const val MY_REDIRECT_URI:String ="myproject://www.exagfdasrvxcmple.com/gizmos"
        const val SECRET_KEY:String ="e1guRuEqxqtvMOf9L3_Sf_S_z1P8cs41C720MpfdWqw"
    }
}

