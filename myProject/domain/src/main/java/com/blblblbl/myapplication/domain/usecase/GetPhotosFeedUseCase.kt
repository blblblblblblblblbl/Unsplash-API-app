package com.blblblbl.myapplication.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosFeedUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Flow<PagingData<Photo>> {
        return repository.getAllImgs()
    }
}