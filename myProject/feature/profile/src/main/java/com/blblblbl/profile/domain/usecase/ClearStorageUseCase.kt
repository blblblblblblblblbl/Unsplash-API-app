package com.blblblbl.profile.domain.usecase

import com.blblblbl.profile.domain.repository.UserRepository
import javax.inject.Inject

class ClearStorageUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute() =
        repository.clearStorage()

}