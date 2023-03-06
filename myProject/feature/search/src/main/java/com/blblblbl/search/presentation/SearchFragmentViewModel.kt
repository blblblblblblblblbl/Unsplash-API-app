package com.blblblbl.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.search.domain.model.photo.Photo
import com.blblblbl.search.domain.usecase.LikeStateUseCase
import com.blblblbl.search.domain.usecase.SearchImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val likeStateUseCase: LikeStateUseCase,
    private val searchImagesUseCase: SearchImagesUseCase
):ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<Flow<PagingData<Photo>>?>(null)
    val searchedImages = _searchedImages.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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
    fun search(query: String) {
        viewModelScope.launch {
            _searchedImages.value = searchImagesUseCase.execute(query = query,PAGE_SIZE).cachedIn(viewModelScope)
        }
    }
    companion object{
        const val PAGE_SIZE:Int = 10
    }
}