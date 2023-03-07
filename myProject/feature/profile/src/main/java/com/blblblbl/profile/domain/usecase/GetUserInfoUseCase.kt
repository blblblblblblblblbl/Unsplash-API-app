package com.blblblbl.profile.domain.usecase


import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(username:String): PublicUserInfo?{
        try {
            return repository.getPublicUserInfo(username)
        }
        catch (e:Throwable){
            return null
        }
    }
}