package com.blblblbl.search.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.search.domain.model.photo.Photo
import com.blblblbl.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    fun execute(query: String,pageSize: Int): Flow<PagingData<Photo>>{
        return repository.searchImgByTags(query,pageSize)
    }
}