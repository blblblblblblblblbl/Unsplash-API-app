package com.blblblbl.myapplication.domain.usecase


import android.net.Uri
import android.util.Log
import com.blblblbl.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class GetBearerTokenUseCase @Inject constructor(
    private val repository: Repository
){
    fun execute(redirectUri:Uri, authSuccess: MutableStateFlow<Boolean?>){
        val start = START.length
        var code = redirectUri.toString().substring(start,redirectUri.toString().length)
        code = code.substringBefore("&")
        repository.authorize(code,authSuccess)
    }
    companion object{
        const val START:String = "myproject://www.exagfdasrvxcmple.com/gizmos?code="
    }
}