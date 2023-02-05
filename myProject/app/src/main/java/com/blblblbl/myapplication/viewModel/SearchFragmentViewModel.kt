package com.blblblbl.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.domain.usecase.LikeUseCase
import com.blblblbl.myapplication.domain.usecase.SearchImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val likeUseCase: LikeUseCase,
    private val searchImagesUseCase: SearchImagesUseCase
):ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedImages = _searchedImages

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
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
    fun search(query: String) {
        viewModelScope.launch {
            Log.d("MyLog", "viewModel search start, query: \"$query\" ")
            searchImagesUseCase.searchImages(query = query).cachedIn(viewModelScope).collect {
                Log.d("MyLog", "viewModel search: $it")
                _searchedImages.value = it
            }
        }
    }
}