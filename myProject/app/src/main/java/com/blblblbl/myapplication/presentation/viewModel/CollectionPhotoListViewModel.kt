package com.blblblbl.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.usecase.GetCollectionPhotoPagingUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionPhotoListViewModel @Inject constructor(
    private val likeStateUseCase: LikeStateUseCase,
    private val pagingFlow: GetCollectionPhotoPagingUseCase
):ViewModel() {
    private val _pagedPhotos = MutableStateFlow<Flow<PagingData<Photo>>?>(null)
    val pagedPhotos = _pagedPhotos.asStateFlow()
    fun getCollectionPhotos(id:String){
        _pagedPhotos.value = pagingFlow.execute(id,PAGE_SIZE).cachedIn(viewModelScope)
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
    companion object {
        const val PAGE_SIZE:Int = 10
    }
}