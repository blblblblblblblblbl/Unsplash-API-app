package com.blblblbl.myapplication.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.usecase.GetPhotosFeedUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val likeStateUseCase: LikeStateUseCase,
    private val getPhotosFeedUseCase: GetPhotosFeedUseCase
):ViewModel() {
    lateinit var pagedPhotos: Flow<PagingData<Photo>>
    fun getPhotosFeed(){
        Log.d("MyLog", "getPhotosFeed()")
        pagedPhotos = getPhotosFeedUseCase.execute(PAGE_SIZE)
    }
    fun changeLike(id: String, bool:Boolean){
        viewModelScope.launch {
            if (bool){
                likeStateUseCase.like(id)
            }
            else{
                likeStateUseCase.unlike(id)
            }
        }
    }
    companion object{
        const val PAGE_SIZE:Int = 10
    }
}