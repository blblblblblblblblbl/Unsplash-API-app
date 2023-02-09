package com.blblblbl.myapplication.presentation.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.usecase.DownloadImgUseCase
import com.blblblbl.myapplication.domain.usecase.GetDetailedPhotoInfoUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoDetailedInfoFragmentViewModel @Inject constructor(
    private val getPhotosUseCase: GetDetailedPhotoInfoUseCase,
    private val likeStateUseCase: LikeStateUseCase,
    private val downloadImgUseCase: DownloadImgUseCase,
):ViewModel() {
    private val _detailedPhotoInfo = MutableStateFlow<com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo?>(null)
    var status = MutableLiveData<Boolean?>()
    var intent :Intent? = null
    val detailedPhotoInfo = _detailedPhotoInfo.asStateFlow()
    fun getPhotoById(id:String){
        viewModelScope.launch {
            val response = getPhotosUseCase.execute(id)
            _detailedPhotoInfo.value = response
            Log.d("MyLog","single photo by id response:${response}")
        }
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
    fun download(){
        _detailedPhotoInfo.value?.let { downloadImgUseCase.execute(it) }
    }


}