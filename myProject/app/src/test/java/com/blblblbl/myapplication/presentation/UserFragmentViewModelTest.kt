package com.blblblbl.myapplication.presentation

import android.content.Context
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.blblblbl.myapplication.MainDispatcherRule
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import com.blblblbl.myapplication.domain.usecase.*
import com.blblblbl.myapplication.presentation.viewModel.UserFragmentViewModel
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

class UserFragmentViewModelTest {
    val context = mock<Context>()
    val getUserInfoUseCase = mock<GetUserInfoUseCase>()
    val likeStateUseCase = mock<LikeStateUseCase>()
    val getMeInfoUseCase: GetMeInfoUseCase = mock()
    val clearStorageUseCase: ClearStorageUseCase = mock()
    val getLikedPhotoPagingUseCase: GetLikedPhotoPagingUseCase = mock()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: UserFragmentViewModel
    @After
    fun afterEach(){
        Mockito.reset(getUserInfoUseCase)
        Mockito.reset(likeStateUseCase)
        Mockito.reset(getMeInfoUseCase)
        Mockito.reset(clearStorageUseCase)
        Mockito.reset(getLikedPhotoPagingUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = UserFragmentViewModel(context,getUserInfoUseCase,likeStateUseCase,getMeInfoUseCase,clearStorageUseCase,getLikedPhotoPagingUseCase)
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
    fun logoutTest()= runTest{
        runBlocking { viewModel.logout() }
        runBlocking { verify(clearStorageUseCase, times(1)).execute() }
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
    fun getUserInfoTest()= runTest{
        runBlocking { viewModel.getUserInfo() }
        val userInfo = UserInfo(username = "vbfheqejokl")
        val userNameCaptor = argumentCaptor<String>()
        val publicUserInfo = PublicUserInfo()
        Mockito.`when`(getMeInfoUseCase.execute()).thenReturn(userInfo)
        Mockito.`when`(getUserInfoUseCase.execute(userInfo.username!!)).thenReturn(publicUserInfo)
        val photo = PagingData.from(listOf(Photo()))
        val result : Flow<PagingData<Photo>> = flow {
            emit(photo)
        }
        Mockito.`when`(getLikedPhotoPagingUseCase.execute(userInfo.username!!,UserFragmentViewModel.PAGE_SIZE))
            .thenReturn(result)
        runBlocking { verify(getMeInfoUseCase, times(1)).execute() }
        viewModel.getUserInfo()
        runBlocking { verify(getUserInfoUseCase, times(1)).execute(userNameCaptor.capture()) }
        Assert.assertEquals(userInfo.username,userNameCaptor.firstValue)
        Assert.assertEquals(userInfo,viewModel.privateUserInfo.value)
        Assert.assertEquals(publicUserInfo,viewModel.publicUserInfo.value)
    }
}