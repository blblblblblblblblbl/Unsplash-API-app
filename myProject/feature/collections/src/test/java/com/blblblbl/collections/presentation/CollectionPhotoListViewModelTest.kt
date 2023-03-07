package com.blblblbl.collections.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.blblblbl.collections.domain.model.photo.Photo
import com.blblblbl.collections.domain.usecase.GetCollectionPhotoPagingUseCase
import com.blblblbl.collections.domain.usecase.LikeStateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CollectionPhotoListViewModelTest {
    val likeStateUseCase = mock<LikeStateUseCase>()
    val getCollectionPhotoPagingUseCase = mock<GetCollectionPhotoPagingUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: CollectionPhotoListViewModel
    @After
    fun afterEach(){
        Mockito.reset(likeStateUseCase)
        Mockito.reset(getCollectionPhotoPagingUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = CollectionPhotoListViewModel(likeStateUseCase,getCollectionPhotoPagingUseCase)
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
    fun getCollectionPhotos(){
        val id ="vwedfbjhka"
        val idCaptor = argumentCaptor<String>()
        val pageSizeCaptor = argumentCaptor<Int>()
        val pageSize = CollectionPhotoListViewModel.PAGE_SIZE
        val photo = PagingData.from(listOf(Photo()))
        val result : Flow<PagingData<Photo>> = flow {
            emit(photo)
        }
        Mockito.`when`(getCollectionPhotoPagingUseCase.execute(id,pageSize))
            .thenReturn(result)
        viewModel.getCollectionPhotos(id)

        verify(getCollectionPhotoPagingUseCase, times(1)).execute(idCaptor.capture(),pageSizeCaptor.capture())
        Assert.assertEquals(id,idCaptor.firstValue)
        Assert.assertEquals(pageSize,pageSizeCaptor.firstValue)
        //Assert.assertEquals(result,viewModel.pagedPhotos) works only without cached in(ViewModelScope)
    }
    @Test
    fun likeTest() {
        val id ="vwedfbjhka"
        val isLike = true
        val idCaptor = argumentCaptor<String>()
        Assert.assertEquals(true,true)
        runBlocking {
            viewModel.changeLike(id,isLike) }


        runBlocking {
            Mockito.verify(likeStateUseCase, times(1)).like(idCaptor.capture())
        }

        Assert.assertEquals(id,idCaptor.firstValue)
    }
    @Test
    fun unlikeTest() {
        val id ="vwedfbjhka"
        val isLike = false
        val idCaptor = argumentCaptor<String>()
        Assert.assertEquals(true,true)
        runBlocking {
            viewModel.changeLike(id,isLike) }


        runBlocking {
            Mockito.verify(likeStateUseCase, times(1)).unlike(idCaptor.capture())
        }

        Assert.assertEquals(id,idCaptor.firstValue)
    }
}

