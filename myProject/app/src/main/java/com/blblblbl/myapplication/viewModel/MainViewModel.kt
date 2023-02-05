package com.blblblbl.myapplication.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.blblblbl.myapplication.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
):ViewModel(){
    fun saveAuthToken(uri:Uri){
        authUseCase.execute(uri)
    }
}