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

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val _openAuthPageFlow = MutableStateFlow<Intent?>(null)
    val openAuthPageFlow = _openAuthPageFlow.asStateFlow()
    /*fun auth(
        context: Context,
        completeIntent: PendingIntent,
        cancelIntent: PendingIntent
    ){
        val authService = AuthorizationService(context)
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://unsplash.com/oauth/authorize"),  // authorization endpoint
            Uri.parse("https://unsplash.com/oauth/token")) // token endpoint
        var authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,  // the authorization service configuration
            MY_CLIENT_ID,  // the client ID, typically pre-registered and static
            ResponseTypeValues.CODE,  // the response_type value: we want a code
            MY_REDIRECT_URI.toUri()
        ) // the redirect URI to which the auth response is sent
        var authRequest = authRequestBuilder
            .setScope("public read_user write_user read_photos write_photos write_likes write_followers write_collections read_collections")
            .build()
        authService.performAuthorizationRequest(
            authRequest,
            completeIntent,
            cancelIntent
            //PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE),
            //PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)
        )
    }*/

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
    companion object AuthConfig{
        const val AUTH_URI = "https://unsplash.com/oauth/authorize"
        const val TOKEN_URI = "https://unsplash.com/oauth/token"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "public read_user write_user read_photos write_photos write_likes write_followers write_collections read_collections"

        const val CLIENT_ID:String ="RoIF7WHVqFj86IPcmNSz_dKxmUaDRGANTaSk9aqnyac"
        const val CLIENT_SECRET:String ="e1guRuEqxqtvMOf9L3_Sf_S_z1P8cs41C720MpfdWqw"
        const val REDIRECT_URI:String ="myunsplashproject://unsplash.com/callback"
    }
}