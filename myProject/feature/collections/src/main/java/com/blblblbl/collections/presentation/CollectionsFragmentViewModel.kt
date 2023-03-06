package com.blblblbl.collections.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.domain.usecase.GetCollectionsPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsFragmentViewModel @Inject constructor(
    private val getCollectionsPagingUseCase: GetCollectionsPagingUseCase
):ViewModel() {
    lateinit var pagedCollections: Flow<PagingData<PhotoCollection>>
    fun getCollections(){
        pagedCollections = getCollectionsPagingUseCase.execute(PAGE_SIZE).cachedIn(viewModelScope)
    }
    companion object {
        const val PAGE_SIZE:Int = 10
    }
}