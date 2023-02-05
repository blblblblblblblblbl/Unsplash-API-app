package com.blblblbl.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.repository.paging_sources.CollectionPhotoPagingSource
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionPhotoListViewModel @Inject constructor(
    private val collectionPhotosPagingSource: CollectionPhotoPagingSource,
    private val likeStateUseCase: LikeStateUseCase
):ViewModel() {
    lateinit var pagedPhotos: Flow<PagingData<Photo>>
    fun getCollectionPhotos(id:String){
        collectionPhotosPagingSource.idInit(id)
        pagedPhotos = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { collectionPhotosPagingSource }
        ).flow.cachedIn(viewModelScope)
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
}