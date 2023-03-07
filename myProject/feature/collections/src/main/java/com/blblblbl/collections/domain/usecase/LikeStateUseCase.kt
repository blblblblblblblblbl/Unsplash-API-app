package com.blblblbl.collections.domain.usecase

import com.blblblbl.collections.domain.repository.CollectionRepository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend fun like(id: String) =
        repository.like(id)


    suspend fun unlike(id: String) =
        repository.unlike(id)

}