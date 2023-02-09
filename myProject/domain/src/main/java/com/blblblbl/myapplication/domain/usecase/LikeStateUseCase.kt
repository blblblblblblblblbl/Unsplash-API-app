package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.domain.repository.Repository
import javax.inject.Inject

class LikeStateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun like(id: String){
        repository.like(id)
    }
    suspend fun unlike(id: String){
        repository.unlike(id)
    }
}