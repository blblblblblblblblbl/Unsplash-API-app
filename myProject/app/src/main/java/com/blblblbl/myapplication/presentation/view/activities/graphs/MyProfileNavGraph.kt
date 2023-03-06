package com.blblblbl.myapplication.presentation.view.activities.graphs

import android.content.Intent
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.blblblbl.myapplication.presentation.view.activities.MainActivity
import com.blblblbl.myapplication.presentation.view.activities.MyProfile
import com.blblblbl.myapplication.presentation.view.activities.graphs.MyProfileNav.DETAILED_PHOTO_ROUTE
import com.blblblbl.myapplication.presentation.view.fragments.PhotoDetailedFragmentCompose
import com.blblblbl.myapplication.presentation.view.fragments.PhotoDetailedInfoFragment
import com.blblblbl.myapplication.presentation.view.fragments.UserFragmentTab
import com.blblblbl.profile.ui.UserFragmentCompose

fun NavGraphBuilder.myProfileGraph(navController: NavHostController) {
    navigation(startDestination = MyProfile.route, route = "MyProfileNested") {
        composable(route = MyProfile.route) {
            val context = LocalContext.current
            UserFragmentCompose(
                photoOnClick = {photo->
                    navController.navigate("${DETAILED_PHOTO_ROUTE}/${photo.id}")
                },
                Intent(context, MainActivity::class.java)
            )
            /*UserFragmentTab(
                photoOnClick = {photo->
                    navController.navigate("${DETAILED_PHOTO_ROUTE}/${photo.id}")
                }
            )*/
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

private object MyProfileNav {
    const val DETAILED_PHOTO_ROUTE = "MyProfileNav.DetailedPhoto"
}