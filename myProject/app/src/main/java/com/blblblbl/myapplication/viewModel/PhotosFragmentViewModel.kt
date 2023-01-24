package com.blblblbl.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.data.PhotosPagingSource
import com.blblblbl.myapplication.domain.GetPhotosUseCase
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.repository.Repository
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.domain.LikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val photosPagingSource: PhotosPagingSource,
    private val repository: Repository,
    private val likeUseCase: LikeUseCase
):ViewModel() {
    val pagedPhotos: Flow<PagingData<DBPhoto>> =repository.getAllImages()
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
    fun getPhotos(page: Int){
        viewModelScope.launch {
            Log.d("MyLog", getPhotosUseCase.getPhotos(page).toString() )
        }
    }
}