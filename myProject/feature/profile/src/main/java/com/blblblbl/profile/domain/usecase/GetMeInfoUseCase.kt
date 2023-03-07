package com.blblblbl.profile.domain.usecase

import com.blblblbl.profile.domain.model.user_info.UserInfo
import com.blblblbl.profile.domain.repository.UserRepository
import javax.inject.Inject

class GetMeInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(): UserInfo? =
        repository.getMeInfo()

}