package com.blblblbl.myapplication.presentation

import android.net.Uri
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.core.net.toUri
import com.blblblbl.myapplication.MainDispatcherRule
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
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
    val getSavedBearerTokenUseCase = mock<GetSavedBearerTokenUseCase>()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    lateinit var viewModel: MainViewModel
    @After
    fun afterEach(){
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @Before
    fun beforeEach(){
        viewModel = MainViewModel(
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