package com.blblblbl.myapplication.presentation.view.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.databinding.ActivityMainBinding
import com.blblblbl.myapplication.presentation.view.fragments.PhotoDetailedInfoFragment
import com.blblblbl.myapplication.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        val redirectUri: Uri? = intent.data
        if (redirectUri.toString().startsWith("myproject://www.exagfdasrvxcmple.com/gizmos?code=")){
            viewModel.saveAuthToken(redirectUri!!)
            lifecycleScope.launchWhenCreated { viewModel.authSuccess.collect {
                it?.let {
                    if (it){
                        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                        val navController = navHostFragment.navController
                        binding.bottomNav.setupWithNavController(navController)
                        setContentView(binding.root)
                    }
                }
            }
            }
        }
        else if (redirectUri.toString().startsWith("https://unsplash.com/photos/")){
            val bundle = bundleOf()
            val start = "https://unsplash.com/photos/".length
            var id = redirectUri.toString().substring(start,redirectUri.toString().length)
            bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, id)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_photosFragment_to_photoDetailedInfoFragment,bundle)
            binding.bottomNav.setupWithNavController(navController)
            setContentView(binding.root)
        }
        else{
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            binding.bottomNav.setupWithNavController(navController)
            setContentView(binding.root)
        }
    }
}