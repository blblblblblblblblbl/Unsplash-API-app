package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.data.repository.Repository
import com.example.example.UserInfo
import javax.inject.Inject

class GetMeInfoUseCase@Inject constructor(
    private val repository: Repository
){
    suspend fun execute(): UserInfo?{
        return repository.getUserInfo()
    }
}