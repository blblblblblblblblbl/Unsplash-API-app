package com.blblblbl.myapplication.presentation

import android.content.Context
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
import com.blblblbl.myapplication.presentation.viewModel.AuthViewModel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class AuthViewModelTest {
    val getSavedBearerTokenUseCase = mock<GetSavedBearerTokenUseCase>()
    val context = mock<Context>()
    lateinit var viewModel:AuthViewModel
    @AfterEach
    fun afterEach(){
        Mockito.reset(getSavedBearerTokenUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
    @BeforeEach
    fun beforeEach(){
        viewModel = AuthViewModel(context,getSavedBearerTokenUseCase)
        ArchTaskExecutor.getInstance().setDelegate(object :TaskExecutor(){
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
    fun rememberedAuthTest(){
        viewModel.rememberedAuth()
        verify(getSavedBearerTokenUseCase, times(1)).execute()
    }
}