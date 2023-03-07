package com.blblblbl.profile.domain.usecase

import androidx.paging.PagingData
import com.blblblbl.profile.domain.model.photo.Photo
import com.blblblbl.profile.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedPhotoPagingUseCase @Inject constructor(
    private val repository: UserRepository
){
    fun execute(userName:String,pageSize:Int): Flow<PagingData<Photo>> {
        return repository.getLikedPhotosPagingDataFlow(userName,pageSize)
    }

}