package com.blblblbl.auth.presentation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.auth.domain.usecase.GetAuthRequestUseCase
import com.blblblbl.auth.domain.usecase.PerformTokenRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.*
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthRequestUseCase: GetAuthRequestUseCase,
    private val performTokenRequestUseCase: PerformTokenRequestUseCase
):ViewModel() {

    private val _openAuthPageFlow = MutableStateFlow<Intent?>(null)
    val openAuthPageFlow = _openAuthPageFlow.asStateFlow()


    fun auth(
        context: Context

    ){
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = getAuthRequestUseCase.execute()

        val authService = AuthorizationService(context)

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
        _openAuthPageFlow.value = openAuthPageIntent
    }
    fun onAuthCodeReceived(
        tokenRequest: TokenRequest,
        context: Context,
        onAuthSucces: () -> Unit
    ) {
        val authService = AuthorizationService(context)
        viewModelScope.launch {
            runCatching {
                performTokenRequestUseCase.execute(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                Log.d("MyLog","token Success ")
                onAuthSucces()
            }.onFailure {
                Log.d("MyLog","token Failure")
            }
        }
    }

}