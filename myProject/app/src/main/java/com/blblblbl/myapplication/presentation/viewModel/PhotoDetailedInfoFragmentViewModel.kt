package com.blblblbl.myapplication.presentation.viewModel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.photo_detailed.Location
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

    private val _detailedPhotoInfo = MutableStateFlow<DetailedPhotoInfo?>(null)
    val detailedPhotoInfo = _detailedPhotoInfo.asStateFlow()

    private val _isToShowLocation = MutableStateFlow<Boolean>(false)
    val isToShowLocation = _isToShowLocation.asStateFlow()

    var intent :Intent? = null

    fun getPhotoById(id:String){
        viewModelScope.launch {
            val response = getPhotosUseCase.execute(id)
            _detailedPhotoInfo.value = response
            detailedPhotoInfo.value?.location?.let{_isToShowLocation.value =  showLocationButtonCondition(it) }
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
    private fun showLocationButtonCondition(location: Location):Boolean{
        val isLocName = location.city!=null|| location.country!=null
        val isLocNotNull = location.position?.latitude!=null&& location.position?.longitude!=null
        val isLocNotZero = location.position?.latitude!=0.0|| location.position?.longitude!=0.0
        return (isLocName||(isLocNotNull&&isLocNotZero))
    }


}