package com.blblblbl.auth.data.repository

import android.util.Log
import com.blblblbl.auth.data.AppAuth
import com.blblblbl.auth.data.model.TokensModel
import com.blblblbl.auth.data.persistent_storage.PersistentStorage
import com.blblblbl.auth.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val persistentStorage: PersistentStorage
):AuthRepository {
    override fun getAuthRequest(): AuthorizationRequest =
        AppAuth.getAuthRequest()



    override suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,tokens.accessToken)
        Log.d("MyLog","6. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
    }
}