package com.blblblbl.myapplication.domain.usecase

import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.repository.Repository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(username:String):PublicUserInfo?{
        try {
            return repository.getPublicUserInfo(username)
        }
        catch (e:Throwable){
            return null
        }
    }
}