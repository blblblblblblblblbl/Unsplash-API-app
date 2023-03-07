package com.blblblbl.myapplication.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.domain.repository.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import net.openid.appauth.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val persistentStorage: PersistentStorage
):Repository {
    override fun authorize(code: String, authSuccess: MutableStateFlow<Boolean?>) {
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
                    persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,resp.accessToken.toString())
                    authSuccess.value = true
                    // exchange succeeded
                } else {
                    ex?.printStackTrace()
                    authSuccess.value = false
                    // authorization failed, check ex for more details
                }
            })
    }

    override fun getSavedBearerToken(): String? {
        return persistentStorage.getProperty(PersistentStorage.AUTH_TOKEN)
    }
    companion object{
        const val MY_CLIENT_ID:String ="RoIF7WHVqFj86IPcmNSz_dKxmUaDRGANTaSk9aqnyac"
        const val MY_REDIRECT_URI:String ="myproject://www.exagfdasrvxcmple.com/gizmos"
        const val SECRET_KEY:String ="e1guRuEqxqtvMOf9L3_Sf_S_z1P8cs41C720MpfdWqw"
    }
}