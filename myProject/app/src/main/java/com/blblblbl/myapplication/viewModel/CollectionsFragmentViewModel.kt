package com.blblblbl.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blblblbl.myapplication.data.repository.paging_sources.CollectionsPagingSource
import com.blblblbl.myapplication.data.repository.Repository
import com.example.example.PhotoCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsFragmentViewModel @Inject constructor(
    private val collectionsPagingSource: CollectionsPagingSource,
    private val repository: Repository
):ViewModel() {
    fun getCollections(page:Int){
        viewModelScope.launch {
            repository.getCollections(page)
        }
    }
    val pagedCollections: Flow<PagingData<PhotoCollection>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { collectionsPagingSource }
    ).flow.cachedIn(viewModelScope)
}