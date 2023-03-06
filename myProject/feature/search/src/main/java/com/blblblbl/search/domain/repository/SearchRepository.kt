package com.blblblbl.search.domain.repository

import androidx.paging.PagingData
import com.blblblbl.search.domain.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchImgByTags(query: String,pageSize: Int): Flow<PagingData<Photo>>
    suspend fun like(id: String)
    suspend fun unlike(id: String)
}