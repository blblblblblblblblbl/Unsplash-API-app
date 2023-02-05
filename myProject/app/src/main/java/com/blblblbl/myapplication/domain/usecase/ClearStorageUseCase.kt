package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.data.repository.Repository
import javax.inject.Inject

class ClearStorageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(){
        repository.clearDB()
    }
}