package com.blblblbl.detailedphoto.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.blblblbl.detailedphoto.domain.model.photo_detailed.DetailedPhotoInfo
import com.blblblbl.detailedphoto.domain.usecase.DownloadImgUseCase
import com.blblblbl.detailedphoto.domain.usecase.GetDetailedPhotoInfoUseCase
import com.blblblbl.detailedphoto.domain.usecase.LikeStateUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class PhotoDetailedInfoFragmentViewModelTest {
    val getDetailedInfoUseCase: GetDetailedPhotoInfoUseCase = mock<GetDetailedPhotoInfoUseCase>()
    val likeStateUseCase: LikeStateUseCase = mock<LikeStateUseCase>()
    val downloadImgUseCase: DownloadImgUseCase = mock<DownloadImgUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: PhotoDetailedInfoFragmentViewModel
    @After
    fun afterEach(){
        Mockito.reset(getDetailedInfoUseCase)
        Mockito.reset(likeStateUseCase)
        Mockito.reset(downloadImgUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = PhotoDetailedInfoFragmentViewModel(getDetailedInfoUseCase,likeStateUseCase,downloadImgUseCase)
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
    fun getPhotoByIdTest()= runTest{
        val detailedPhotoInfo = DetailedPhotoInfo()
        val id = "gertnjhudlis"
        val idCaptor = argumentCaptor<String>()
        Mockito.`when`(getDetailedInfoUseCase.execute(id)).thenReturn(detailedPhotoInfo)
        runBlocking {
            viewModel.getPhotoById(id)}
        runBlocking {
            verify(getDetailedInfoUseCase, times(1)).execute(idCaptor.capture())
        }
        Assert.assertEquals(id,idCaptor.firstValue)
        Assert.assertEquals(detailedPhotoInfo.hashCode(),viewModel.detailedPhotoInfo.value.hashCode())

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
    fun downloadNotNullTest()= runTest{
        val detailedPhotoInfo = DetailedPhotoInfo()
        val detailedPhotoInfoCaptor = argumentCaptor<DetailedPhotoInfo>()
        val id = "gertnjhudlis"
        val idCaptor = argumentCaptor<String>()
        Mockito.`when`(getDetailedInfoUseCase.execute(id)).thenReturn(detailedPhotoInfo)
        runBlocking {
            viewModel.getPhotoById(id)}
        runBlocking {
            verify(getDetailedInfoUseCase, times(1)).execute(idCaptor.capture())
        }
        viewModel.download()
        verify(downloadImgUseCase, times(1)).execute(detailedPhotoInfoCaptor.capture())
    }
    @Test
    fun downloadNullTest()= runTest{
        val detailedPhotoInfo = null
        val detailedPhotoInfoCaptor = argumentCaptor<DetailedPhotoInfo>()
        val id = "gertnjhudlis"
        val idCaptor = argumentCaptor<String>()
        Mockito.`when`(getDetailedInfoUseCase.execute(id)).thenReturn(detailedPhotoInfo)
        runBlocking {
            viewModel.getPhotoById(id)}
        runBlocking {
            verify(getDetailedInfoUseCase, times(1)).execute(idCaptor.capture())
        }
        viewModel.download()
        verify(downloadImgUseCase, times(0)).execute(detailedPhotoInfoCaptor.capture())
    }
}