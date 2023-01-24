package com.blblblbl.myapplication

import android.R.attr
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blblblbl.myapplication.databinding.ActivityMainBinding
import com.blblblbl.myapplication.view.PhotoDetailedInfoFragment
import com.blblblbl.myapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val redirectUri: Uri? = intent.data
        if (redirectUri.toString().startsWith("myproject://www.exagfdasrvxcmple.com/gizmos?code=")){
            viewModel.saveAuthToken(redirectUri!!)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        if (redirectUri.toString().startsWith("https://unsplash.com/photos/")){
            val bundle = bundleOf()
            val start = "https://unsplash.com/photos/".length
            var id = redirectUri.toString().substring(start,redirectUri.toString().length)
            bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, id)
            navController.navigate(R.id.action_photosFragment_to_photoDetailedInfoFragment,bundle)
        }
        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)

    }


}