package com.blblblbl.myapplication.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosFeedUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Flow<PagingData<DBPhoto>> {
        return repository.getAllImgs()
    }
}