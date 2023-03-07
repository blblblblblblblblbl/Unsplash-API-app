package com.blblblbl.myapplication.presentation

import android.net.Uri
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.core.net.toUri
import com.blblblbl.myapplication.MainDispatcherRule
import com.blblblbl.myapplication.domain.usecase.GetBearerTokenUseCase
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
import com.blblblbl.myapplication.presentation.viewModel.CollectionPhotoListViewModel
import com.blblblbl.myapplication.presentation.viewModel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {
    val getBearerTokenUseCase = mock<GetBearerTokenUseCase>()
    val getSavedBearerTokenUseCase = mock<GetSavedBearerTokenUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: MainViewModel
    @After
    fun afterEach(){
        Mockito.reset(getBearerTokenUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = MainViewModel(
            getBearerTokenUseCase,
            getSavedBearerTokenUseCase
        )
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
    fun saveAuthTokenTest(){
        val uri = "grfebwhjwberj".toUri()
        val uriCaptor = argumentCaptor<Uri>()
        val authSuccessCaptor = argumentCaptor<MutableStateFlow<Boolean?>>()
        viewModel.saveAuthToken(uri)
        verify(getBearerTokenUseCase, times(1)).execute(uriCaptor.capture(),authSuccessCaptor.capture())
        Assert.assertEquals(uriCaptor.firstValue,uri)
        Assert.assertEquals(authSuccessCaptor.firstValue,viewModel.authSuccess)
    }
    @Test
    fun checkOnSavedTokenNullTest(){
        Mockito.`when`(getSavedBearerTokenUseCase.execute()).thenReturn(null)
        val result = viewModel.checkOnSavedToken()
        verify(getSavedBearerTokenUseCase, times(1)).execute()
        Assert.assertEquals(result, false)
    }
    @Test
    fun checkOnSavedTokenTest(){
        Mockito.`when`(getSavedBearerTokenUseCase.execute()).thenReturn("frwedavbgjhk")
        val result = viewModel.checkOnSavedToken()
        verify(getSavedBearerTokenUseCase, times(1)).execute()
        Assert.assertEquals(result, true)
    }
}