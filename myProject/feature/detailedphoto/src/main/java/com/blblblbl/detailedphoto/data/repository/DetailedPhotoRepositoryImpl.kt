package com.blblblbl.detailedphoto.data.repository

import android.content.Context
import androidx.work.*
import com.blblblbl.detailedphoto.data.datasource.DetailedPhotoDataSource
import com.blblblbl.detailedphoto.data.utils.mapToDomain
import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailedPhotoRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val detailedPhotoDataSource: DetailedPhotoDataSource
): DetailedPhotoRepository {
    override suspend fun getDetailedImgInfoById(id: String): DetailedPhotoInfo {
        return detailedPhotoDataSource.getPhotoById(id).mapToDomain()?:DetailedPhotoInfo()
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

    override suspend fun like(id: String){
        detailedPhotoDataSource.like(id)
    }
    override suspend fun unlike(id: String){
        detailedPhotoDataSource.unlike(id)
    }
}