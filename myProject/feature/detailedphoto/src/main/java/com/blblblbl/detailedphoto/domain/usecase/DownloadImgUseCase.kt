package com.blblblbl.detailedphoto.domain.usecase

import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import javax.inject.Inject

class DownloadImgUseCase @Inject constructor(
    private val repository: DetailedPhotoRepository
) {

    fun execute(detailedPhotoInfo: DetailedPhotoInfo) =
        repository.downloadImg(detailedPhotoInfo)

}