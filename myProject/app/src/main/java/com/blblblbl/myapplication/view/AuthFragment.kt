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
    lateinit var binding: FragmentAuthBinding
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
        ExperimentalPagerApi::class
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        viewModel.rememberedAuth()
        binding.blogin.setOnClickListener {
            viewModel.auth()
        }
        val images = listOf(
            R.drawable.onboarding1,
            R.drawable.onboarding2,
            R.drawable.onboarding3,
            R.drawable.onboarding4,
            )
        val phrases = listOf(
            getString(R.string.onboarding1),
            getString(R.string.onboarding2),
            getString(R.string.onboarding3),
            getString(R.string.onboarding4)
        )
        return ComposeView(requireContext()).apply {
            setContent {
                UnsplashTheme{
                    /*val page = remember { mutableStateOf(FIRST_PAGE) }
                    Scaffold(
                        topBar = { AuthTopBar(
                            onForwardClicked = {
                                if (page.value<images.size) page.value++
                                Log.d("MyLog","page: ${page.value}")},
                            onBackClicked = {
                                if (page.value> FIRST_PAGE) page.value--
                                Log.d("MyLog","page: ${page.value}")},
                            isBackVisible = page.value> FIRST_PAGE,
                            isFrontVisible = page.value< images.size
                        ) }
                    ) {
                        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                            if(page.value<images.size){
                                val image = images[page.value]
                                val phrase = phrases[page.value]
                                onboardingScreen(image,phrase)
                            }
                            else{
                                AuthScreen()
                            }
                        }
                    }*/
                    OnBoardingScreen({ viewModel.auth() })
                }
            }
        }
        //return binding.root
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AuthTopBar(
        onForwardClicked: () -> Unit,
        onBackClicked: () -> Unit,
        isBackVisible:Boolean,
        isFrontVisible:Boolean
    ){
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.onboarding),
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {if (isBackVisible) {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.logout_icon_description),
                    )
                }
            }
                             },
            actions = { if (isFrontVisible){
                IconButton(onClick = { onForwardClicked() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = stringResource(id = R.string.logout_icon_description)
                    )
                }
            }
            }
        )
    }
    @Composable
    fun onboardingScreen(image:Int,phrase:String){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(image),
                contentDescription = ""
            )
            Text(text = phrase)
        }

    }
    @Composable
    fun AuthScreen(){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { viewModel.auth() }) {
                Text(text = stringResource(id = R.string.log_in))
            }
        }
    }
    companion object{
        const val FIRST_PAGE:Int = 0
        const val LAST_PAGE:Int = 4
    }

}