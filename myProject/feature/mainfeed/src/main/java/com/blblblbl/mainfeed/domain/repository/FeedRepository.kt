package com.blblblbl.mainfeed.domain.repository

import androidx.paging.PagingData
import com.blblblbl.mainfeed.domain.model.photos.Photo
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getAllImgs(pageSize: Int): Flow<PagingData<Photo>>
    suspend fun like(id: String)
    suspend fun unlike(id: String)

}