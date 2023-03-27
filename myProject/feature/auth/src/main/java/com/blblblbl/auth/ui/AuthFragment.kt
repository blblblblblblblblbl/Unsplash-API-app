package com.blblblbl.auth.ui

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.auth.presentation.AuthViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse


@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun AuthFragmentCompose(
    onAuthSucces: () -> Unit
){
    val viewModel :AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            val dataIntent = it.data
            dataIntent?.let { dataIntent->
                val tokenExchangeRequest = AuthorizationResponse.fromIntent(dataIntent)
                    ?.createTokenExchangeRequest()
                tokenExchangeRequest?.let {tokenExchangeRequest->
                    viewModel.onAuthCodeReceived(tokenExchangeRequest, context,onAuthSucces)
                }
            }
        })
    val intent = viewModel.openAuthPageFlow.collectAsState()
    if (intent.value!=null){
        launcher.launch(intent.value)
    }
    OnBoardingScreen(
        logInOnClick = { viewModel.auth(
            context
        ) }
    )
}
