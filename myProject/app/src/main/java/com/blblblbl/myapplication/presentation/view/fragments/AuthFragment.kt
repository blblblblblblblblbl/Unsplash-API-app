package com.blblblbl.myapplication.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.myapplication.presentation.view.auth.screen.OnBoardingScreen
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.AuthViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        viewModel.rememberedAuth()
        return ComposeView(requireContext()).apply {
            setContent {
                UnsplashTheme{
                    OnBoardingScreen({ viewModel.auth() })
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun AuthFragmentCompose(){
    val viewModel :AuthViewModel = hiltViewModel()
    OnBoardingScreen(
        logInOnClick = { viewModel.auth() }
    )
}