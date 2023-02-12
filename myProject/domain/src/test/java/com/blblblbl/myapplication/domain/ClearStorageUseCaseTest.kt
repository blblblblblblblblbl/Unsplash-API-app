package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.domain.repository.Repository
import com.blblblbl.myapplication.domain.usecase.ClearStorageUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ClearStorageUseCaseTest {
    @Test
    fun repositoryUsageIsCorrect() = runTest{
        val repository = mock<Repository>()
        val useCase = ClearStorageUseCase(repository)
        launch {
            useCase.execute()
        }
        advanceUntilIdle()
        Mockito.verify(repository, times(1)).clearStorage()
        useCase.execute()
    }
}