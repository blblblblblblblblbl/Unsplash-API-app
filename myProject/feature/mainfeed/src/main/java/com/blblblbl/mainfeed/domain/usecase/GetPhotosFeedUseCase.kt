package com.blblblbl.mainfeed.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.mainfeed.domain.model.photos.Photo
import com.blblblbl.mainfeed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosFeedUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    fun execute(pageSize: Int): Flow<PagingData<Photo>> {
        return repository.getAllImgs(pageSize)
    }
}