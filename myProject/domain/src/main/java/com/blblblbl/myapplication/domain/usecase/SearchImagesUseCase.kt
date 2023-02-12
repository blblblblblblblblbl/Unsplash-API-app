package com.blblblbl.myapplication.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(query: String,pageSize: Int): Flow<PagingData<Photo>>{
        return repository.searchImgByTags(query,pageSize)
    }
}