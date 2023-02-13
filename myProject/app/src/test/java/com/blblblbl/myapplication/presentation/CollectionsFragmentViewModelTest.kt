package com.blblblbl.myapplication.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.blblblbl.myapplication.MainDispatcherRule
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.usecase.GetCollectionsPagingUseCase
import com.blblblbl.myapplication.presentation.viewModel.CollectionPhotoListViewModel
import com.blblblbl.myapplication.presentation.viewModel.CollectionsFragmentViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CollectionsFragmentViewModelTest {
    val getCollectionsPagingUseCase = mock<GetCollectionsPagingUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel:CollectionsFragmentViewModel
    @After
    fun afterEach(){
        Mockito.reset(getCollectionsPagingUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = CollectionsFragmentViewModel(getCollectionsPagingUseCase)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor(){
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }
    @Test
    fun getCollectionsTest(){
        val pageSizeCaptor = argumentCaptor<Int>()
        val pageSize = CollectionsFragmentViewModel.PAGE_SIZE
        val collection = PagingData.from(listOf(PhotoCollection()))
        val result : Flow<PagingData<PhotoCollection>> = flow {
            emit(collection)
        }
        Mockito.`when`(getCollectionsPagingUseCase.execute(pageSize))
            .thenReturn(result)
        viewModel.getCollections()
        verify(getCollectionsPagingUseCase, times(1)).execute(pageSizeCaptor.capture())
        Assert.assertEquals(pageSize,pageSizeCaptor.firstValue)
        //Assert.assertEquals(result,viewModel.pagedPhotos) works only without cached in(ViewModelScope)
    }
}