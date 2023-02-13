package com.blblblbl.myapplication.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.blblblbl.myapplication.MainDispatcherRule
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.usecase.GetPhotosFeedUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import com.blblblbl.myapplication.presentation.viewModel.CollectionPhotoListViewModel
import com.blblblbl.myapplication.presentation.viewModel.PhotosFragmentViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class PhotosFragmentViewModelTest {
    val likeStateUseCase = mock<LikeStateUseCase>()
    val getPhotosFeedUseCase = mock<GetPhotosFeedUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: PhotosFragmentViewModel
    @After
    fun afterEach(){
        Mockito.reset(likeStateUseCase)
        Mockito.reset(getPhotosFeedUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = PhotosFragmentViewModel(likeStateUseCase,getPhotosFeedUseCase)
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
    fun getPhotosFeedTest(){
        val pageSize = CollectionPhotoListViewModel.PAGE_SIZE
        val pageSizeCaptor = argumentCaptor<Int>()
        val photo = PagingData.from(listOf(Photo()))
        val result : Flow<PagingData<Photo>> = flow {
            emit(photo)
        }
        Mockito.`when`(getPhotosFeedUseCase.execute(pageSize))
            .thenReturn(result)
        viewModel.getPhotosFeed()

        verify(getPhotosFeedUseCase, times(1)).execute(pageSizeCaptor.capture())
        Assert.assertEquals(pageSize,pageSizeCaptor.firstValue)
        Assert.assertEquals(result,viewModel.pagedPhotos)// works only without cached in(ViewModelScope)
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