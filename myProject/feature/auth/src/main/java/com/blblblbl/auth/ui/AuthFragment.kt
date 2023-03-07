package com.blblblbl.auth.ui

import android.app.PendingIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.auth.presentation.AuthViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext

// TODO: make this work or delete it
/*@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalPagerApi::class
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                OnBoardingScreen({
                    viewModel.auth()
                })
            }
        }
    }
}*/

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun AuthFragmentCompose(
    completeIntent: PendingIntent,
    cancelIntent: PendingIntent
){
    val viewModel :AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    OnBoardingScreen(
        logInOnClick = { viewModel.auth(
            context,
            completeIntent,
            cancelIntent
        ) }
    )
}