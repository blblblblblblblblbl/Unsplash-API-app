package com.blblblbl.myapplication.presentation.view.activities.graphs

import androidx.compose.material3.Text
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.blblblbl.detailedphoto.ui.PhotoDetailedFragmentCompose
import com.blblblbl.detailedphoto.ui.PhotoDetailedInfoFragment
import com.blblblbl.myapplication.presentation.view.activities.Collections
import com.blblblbl.myapplication.presentation.view.activities.graphs.CollectionsNav.DETAILED_PHOTO_ROUTE
import com.blblblbl.myapplication.presentation.view.activities.graphs.CollectionsNav.PHOTO_LIST_ROUTE
import com.blblblbl.myapplication.presentation.view.fragments.CollectionPhotoListFragment
import com.blblblbl.myapplication.presentation.view.fragments.CollectionPhotoListFragmentCompose
import com.blblblbl.myapplication.presentation.view.fragments.CollectionsFragmentTab

fun NavGraphBuilder.collectionsGraph(navController: NavHostController) {
    navigation(startDestination = Collections.route, route = "CollectionsNested") {
        composable(route = Collections.route) {
            CollectionsFragmentTab(
                collectionOnClick = {collection->
                    navController.navigate("${PHOTO_LIST_ROUTE}/${collection.id}")
                }
            )
        }
        composable(
            route = "$PHOTO_LIST_ROUTE/{${CollectionPhotoListFragment.COLLECTION_ID_KEY}}",
            arguments = listOf(
                navArgument(name = CollectionPhotoListFragment.COLLECTION_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val collectionId =
                navBackStackEntry.arguments?.getString(CollectionPhotoListFragment.COLLECTION_ID_KEY)
            CollectionPhotoListFragmentCompose(
                collectionId = collectionId,
                photoOnClick ={ photo ->
                    navController.navigate("${DETAILED_PHOTO_ROUTE}/${photo.id}")
                }
            )
        }
        composable(
            route = "${DETAILED_PHOTO_ROUTE}/{${PhotoDetailedInfoFragment.PHOTO_ID_KEY}}",
            arguments = listOf(
                navArgument(name = PhotoDetailedInfoFragment.PHOTO_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val photoId = navBackStackEntry.arguments?.getString(PhotoDetailedInfoFragment.PHOTO_ID_KEY)
            if (photoId != null) {
                PhotoDetailedFragmentCompose(photoId = photoId)
            }
            else navController.popBackStack()
        }
    }
}

private object CollectionsNav {
    const val PHOTO_LIST_ROUTE = "CollectionsNav.PhotoList"
    const val DETAILED_PHOTO_ROUTE = "CollectionsNav.DetailedPhoto"
}