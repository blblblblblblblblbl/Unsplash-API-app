package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.data.repository.Repository
import com.example.example.UserInfo
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun getPrivateUserInfo():UserInfo?{
        return repository.getUserInfo()
    }
    suspend fun getPublicUserInfo(username:String):PublicUserInfo?{
        try {
            return repository.getPublicUserInfo(username)
        }
        catch (e:Throwable){
            return null
        }
    }
}