package com.blblblbl.search.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.blblblbl.search.domain.model.photo.Photo
import com.blblblbl.search.domain.usecase.LikeStateUseCase
import com.blblblbl.search.domain.usecase.SearchImagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SearchFragmentViewModelTest {
    val likeStateUseCase = mock<LikeStateUseCase>()
    val searchImagesUseCase = mock<SearchImagesUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: SearchFragmentViewModel
    @After
    fun afterEach(){
        Mockito.reset(likeStateUseCase)
        Mockito.reset(searchImagesUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = SearchFragmentViewModel(likeStateUseCase,searchImagesUseCase)
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
    fun updateSearchQueryTest(){
        val query = "gnertjhkllwrs"
        viewModel.updateSearchQuery(query)
        Assert.assertEquals(viewModel.searchQuery.value,query)
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

    @Test
    fun searchTest() = runTest{
        val query:String = "trgjnklek'werekwnqr["
        val queryCaptor = argumentCaptor<String>()
        val pageSize = SearchFragmentViewModel.PAGE_SIZE
        val pageSizeCaptor = argumentCaptor<Int>()
        val photo = PagingData.from(listOf(Photo()))
        val result : Flow<PagingData<Photo>> = flow {
            emit(photo)
        }
        viewModel.searchQuery.value = query
        Mockito.`when`(searchImagesUseCase.execute(query,pageSize)).thenReturn(result)
        runBlocking { viewModel.search(query) }
        runBlocking { verify(searchImagesUseCase, times(1)).execute(queryCaptor.capture(),pageSizeCaptor.capture()) }
        Assert.assertEquals(query,queryCaptor.firstValue)
        Assert.assertEquals(pageSize,pageSizeCaptor.firstValue)

    }
}