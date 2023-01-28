package com.blblblbl.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.databinding.FragmentAuthBinding
import com.blblblbl.myapplication.view.auth.screen.OnBoardingScreen
import com.blblblbl.myapplication.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.viewModel.AuthViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val viewModel:AuthViewModel by viewModels()
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