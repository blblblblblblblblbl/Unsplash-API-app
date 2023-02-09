package com.blblblbl.myapplication.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.blblblbl.myapplication.domain.usecase.GetBearerTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBearerTokenUseCase: GetBearerTokenUseCase
):ViewModel(){
    fun saveAuthToken(uri:Uri){
        getBearerTokenUseCase.execute(uri)
    }
}