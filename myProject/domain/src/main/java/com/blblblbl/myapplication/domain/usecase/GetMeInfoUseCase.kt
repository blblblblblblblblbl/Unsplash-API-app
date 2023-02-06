package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import javax.inject.Inject

class GetMeInfoUseCase@Inject constructor(
    private val repository: Repository
){
    suspend fun execute(): UserInfo?{
        return repository.getMeInfo()
    }
}