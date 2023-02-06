package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class GetDetailedPhotoInfoUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun execute(id: String): DetailedPhotoInfo {
        return repository.getDetailedImgInfoById(id)
    }
}