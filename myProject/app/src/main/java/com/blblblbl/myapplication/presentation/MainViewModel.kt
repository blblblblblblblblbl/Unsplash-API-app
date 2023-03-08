package com.blblblbl.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.blblblbl.myapplication.domain.usecase.GetSavedBearerTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSavedBearerTokenUseCase:GetSavedBearerTokenUseCase
):ViewModel(){

    fun checkOnSavedToken():Boolean{
        val token = getSavedBearerTokenUseCase.execute()
        return token!=null
    }
}