package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class DownloadImgUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(detailedPhotoInfo: DetailedPhotoInfo){
        repository.downloadImg(detailedPhotoInfo)
    }
}