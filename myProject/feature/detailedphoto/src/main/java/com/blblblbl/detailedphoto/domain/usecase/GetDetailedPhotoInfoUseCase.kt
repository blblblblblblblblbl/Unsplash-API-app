package com.blblblbl.detailedphoto.domain.usecase

import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import javax.inject.Inject

class GetDetailedPhotoInfoUseCase @Inject constructor(
    private val repository: DetailedPhotoRepository
) {
    suspend fun execute(id: String): DetailedPhotoInfo =
        repository.getDetailedImgInfoById(id)

}