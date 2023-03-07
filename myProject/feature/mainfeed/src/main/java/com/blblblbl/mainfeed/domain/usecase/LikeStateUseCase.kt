package com.blblblbl.mainfeed.domain.usecase

import com.blblblbl.mainfeed.domain.repository.FeedRepository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend fun like(id: String) =
        repository.like(id)

    suspend fun unlike(id: String) =
        repository.unlike(id)

}