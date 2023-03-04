package com.blblblbl.myapplication.presentation.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.blblblbl.myapplication.domain.usecase.GetBearerTokenUseCase
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBearerTokenUseCase: GetBearerTokenUseCase,
    private val getSavedBearerTokenUseCase:GetSavedBearerTokenUseCase
):ViewModel(){
    val authSuccess = MutableStateFlow<Boolean?>(null)
    fun saveAuthToken(uri:Uri){
        getBearerTokenUseCase.execute(uri,authSuccess)
    }
    fun checkOnSavedToken():Boolean{
        val token = getSavedBearerTokenUseCase.execute()
        return token!=null
    }
}