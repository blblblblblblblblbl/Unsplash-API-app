package com.blblblbl.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.domain.usecase.GetPhotosFeedUseCase
import com.blblblbl.myapplication.domain.usecase.LikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val likeUseCase: LikeUseCase,
    private val getPhotosFeedUseCase: GetPhotosFeedUseCase
):ViewModel() {
    val pagedPhotos: Flow<PagingData<DBPhoto>> = getPhotosFeedUseCase.execute()
    fun changeLike(id: String, bool:Boolean){
        viewModelScope.launch {
            if (bool){
                likeUseCase.like(id)
            }
            else{
                likeUseCase.unlike(id)
            }
        }
    }
}