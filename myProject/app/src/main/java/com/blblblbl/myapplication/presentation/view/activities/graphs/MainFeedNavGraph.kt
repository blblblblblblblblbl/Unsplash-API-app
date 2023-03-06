package com.blblblbl.myapplication.presentation.view.activities.graphs

import androidx.compose.material3.Text
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.blblblbl.detailedphoto.ui.PhotoDetailedFragmentCompose
import com.blblblbl.detailedphoto.ui.PhotoDetailedInfoFragment
import com.blblblbl.myapplication.presentation.view.activities.MainFeed
import com.blblblbl.myapplication.presentation.view.activities.graphs.MainFeedNav.DETAILED_PHOTO_ROUTE
import com.blblblbl.myapplication.presentation.view.activities.graphs.MainFeedNav.SEARCH_ROUTE
import com.blblblbl.myapplication.presentation.view.fragments.PhotosFragmentTab
import com.blblblbl.myapplication.presentation.view.fragments.SearchFragmentCompose

fun NavGraphBuilder.mainFeedGraph(navController: NavHostController) {
    navigation(startDestination = MainFeed.route, route = "MainFeedNested") {
        composable(route = MainFeed.route) {
            PhotosFragmentTab(
                onSearchClicked = {
                    navController.navigate(SEARCH_ROUTE)
                },
                onPhotoClicked = {photo->
                    navController.navigate("${DETAILED_PHOTO_ROUTE}/${photo.id}")
                }
            )
        }
        composable(route = SEARCH_ROUTE) {
            SearchFragmentCompose(
                closeOnClick = { navController.popBackStack() },
                photoOnClick = { photo -> navController.navigate("${DETAILED_PHOTO_ROUTE}/${photo.id}") }
            )
        }
        composable(
            route = "${DETAILED_PHOTO_ROUTE}/{${PhotoDetailedInfoFragment.PHOTO_ID_KEY}}",
            arguments = listOf(
                navArgument( name = PhotoDetailedInfoFragment.PHOTO_ID_KEY){type = NavType.StringType}
            )
        ) {navBackStackEntry ->
            val photoId = navBackStackEntry.arguments?.getString(PhotoDetailedInfoFragment.PHOTO_ID_KEY)
            if (photoId != null) {
                PhotoDetailedFragmentCompose(photoId = photoId)
            }
            else navController.popBackStack()
        }
    }
}
private object MainFeedNav{
    const val SEARCH_ROUTE = "MainFeedNav.Search"
    const val DETAILED_PHOTO_ROUTE = "MainFeedNav.DetailedPhoto"
}