package com.blblblbl.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.usecase.GetCollectionsPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsFragmentViewModel @Inject constructor(
    private val getCollectionsPagingUseCase: GetCollectionsPagingUseCase
):ViewModel() {
    val pagedCollections: Flow<PagingData<PhotoCollection>> = getCollectionsPagingUseCase.execute(PAGE_SIZE).cachedIn(viewModelScope)
    companion object {
        const val PAGE_SIZE:Int = 10
    }
}