package com.blblblbl.myapplication.presentation

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blblblbl.auth.ui.AuthFragmentCompose
import com.blblblbl.detailedphoto.ui.PhotoDetailedFragmentCompose
import com.blblblbl.mainfeed.ui.PhotosFragmentCompose
import com.blblblbl.myapplication.databinding.ActivityMainBinding
import com.blblblbl.myapplication.navigation.AppTabRow
import com.blblblbl.myapplication.navigation.AuthDest
import com.blblblbl.myapplication.navigation.MainFeed
import com.blblblbl.myapplication.navigation.appTabRowScreens
import com.blblblbl.myapplication.navigation.graphs.collectionsGraph
import com.blblblbl.myapplication.navigation.graphs.mainFeedGraph
import com.blblblbl.myapplication.navigation.graphs.myProfileGraph
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("MyLog","checkOnSavedToken(): true")
        val redirectUri: Uri? = intent.data
        if (redirectUri.toString().startsWith("https://unsplash.com/photos/")) {
            val bundle = bundleOf()
            val start = "https://unsplash.com/photos/".length
            var id = redirectUri.toString().substring(start, redirectUri.toString().length)
            /*bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, id)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_photosFragment_to_photoDetailedInfoFragment, bundle)
            binding.bottomNav.setupWithNavController(navController)
            setContentView(binding.root)*/
            setContent {
                PhotoDetailedFragmentCompose(photoId = id)
            }
        } else {
            /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            binding.bottomNav.setupWithNavController(navController)
            setContentView(binding.root)*/
            if(viewModel.checkOnSavedToken()){
                setContent {
                    AppScreen()
                }
            }

            else{
                setContent {
                    AppScreen(startDestination = AuthDest.route)
                    /*UnsplashTheme() {
                        AuthFragmentCompose(

                        )
                    }*/
                }
            }

        }
    }

}

@Composable
fun AppScreen(startDestination:String = "MainFeedNested") {
    UnsplashTheme() {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = appTabRowScreens.find { it.route == currentDestination?.route }
        Scaffold(
            bottomBar = {
                currentScreen?.let { currentScreen->
                    AppTabRow(
                        allScreens = appTabRowScreens,
                        onTabSelected = { screen ->
                            navController.navigateSingleTopTo(screen.route)
                        },
                        currentScreen = currentScreen
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = startDestination
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination:String = "MainFeedNested"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = AuthDest.route) {
            AuthFragmentCompose(
                onAuthSucces = { navController.navigateAndClear("MainFeedNested") }
            )
        }
        mainFeedGraph(navController)
        collectionsGraph(navController)
        myProfileGraph(navController)
    }
}


fun NavHostController.navigateAndClear(route: String) =
    this.navigate(route) {
        popUpTo(
            route
        ) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }





fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
