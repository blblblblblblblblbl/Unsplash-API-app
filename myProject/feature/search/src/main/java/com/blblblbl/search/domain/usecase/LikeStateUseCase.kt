package com.blblblbl.search.domain.usecase

import com.blblblbl.search.domain.repository.SearchRepository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend fun like(id: String) =
        repository.like(id)

    suspend fun unlike(id: String) =
        repository.unlike(id)

}