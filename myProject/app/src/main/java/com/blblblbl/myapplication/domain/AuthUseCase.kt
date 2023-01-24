package com.blblblbl.myapplication.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.*
import javax.inject.Inject


class AuthUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val persistentStorage: PersistentStorage
){
    //save token in Persistant storage
    fun execute(redirectUri:Uri){
        val start = "myproject://www.exagfdasrvxcmple.com/gizmos?code=".length
        var code = redirectUri.toString().substring(start,redirectUri.toString().length)
        code = code.substringBefore("&")
        Log.d("MyLog",code)
        var authService = AuthorizationService(context)
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://unsplash.com/oauth/authorize"),  // authorization endpoint
            Uri.parse("https://unsplash.com/oauth/token")) // token endpoint
        val clientAuth: ClientAuthentication = ClientSecretBasic(SECRET_KEY)
        authService.performTokenRequest(
            TokenRequest.Builder(serviceConfig, MY_CLIENT_ID)
                .setAuthorizationCode(code)
                .setRedirectUri(MY_REDIRECT_URI.toUri())
                .setGrantType("authorization_code")
                .build(),
            clientAuth,
            AuthorizationService.TokenResponseCallback { resp, ex ->
                if (resp != null) {
                    Log.d(
                        "MyLog",
                        "accessToken:" + resp.accessToken.toString() + "\ntokenType: " + resp.tokenType + "\nscope: " + resp.scopeSet
                    )
                    persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,resp.accessToken.toString())
                    // exchange succeeded
                } else {
                    Log.d("MyLog", ex.toString())
                    // authorization failed, check ex for more details
                }
            })
    }
    companion object{
        const val MY_CLIENT_ID:String ="RoIF7WHVqFj86IPcmNSz_dKxmUaDRGANTaSk9aqnyac"
        const val MY_REDIRECT_URI:String ="myproject://www.exagfdasrvxcmple.com/gizmos"
        const val SECRET_KEY:String ="e1guRuEqxqtvMOf9L3_Sf_S_z1P8cs41C720MpfdWqw"

    }
}