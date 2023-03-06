package com.blblblbl.profile.domain.usecase

import com.blblblbl.profile.domain.repository.UserRepository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun like(id: String){
        repository.like(id)
    }
    suspend fun unlike(id: String){
        repository.unlike(id)
    }
}