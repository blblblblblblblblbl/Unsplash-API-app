package com.blblblbl.detailedphoto.domain.usecase

import com.blblblbl.detailedphoto.domain.repository.DetailedPhotoRepository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: DetailedPhotoRepository
) {

    suspend fun like(id: String) =
        repository.like(id)

    suspend fun unlike(id: String) =
        repository.unlike(id)

}