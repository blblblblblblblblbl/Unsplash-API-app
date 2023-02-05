package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.data.repository.Repository
import javax.inject.Inject

class GetDetailedPhotoInfoUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun execute(id: String): DetailedPhotoInfo {
        return repository.getPhotoById(id)
    }
}