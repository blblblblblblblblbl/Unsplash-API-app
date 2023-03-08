package com.blblblbl.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

interface AppDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object MainFeed : AppDestination {
    override val icon = Icons.Default.Feed
    override val route = "mainFeed"
}
object AuthDest : AppDestination {
    override val icon = Icons.Default.Feed
    override val route = "authroute"
}
object Collections : AppDestination {
    override val icon = Icons.Default.Collections
    override val route = "collections"
}

object MyProfile : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val route = "myProfile"
}
object DetailedPhoto :AppDestination{
    override val icon = Icons.Default.Terrain
    override val route = "detailedPhoto"
}
object Search :AppDestination{
    override val icon = Icons.Default.Terrain
    override val route = "search"
}
object CollectionPhotoList :AppDestination{
    override val icon = Icons.Default.Terrain
    override val route = "CollectionPhotoList"
}

@Preview
@Composable
fun MainFeedIcon(){
    Icon(Icons.Default.Feed,contentDescription = null)
}
@Preview
@Composable
fun CollectionsIcon(){
    Icon(Icons.Default.Collections,contentDescription = null)
}
@Preview
@Composable
fun MyProfileIcon(){
    Icon(Icons.Default.AccountCircle,contentDescription = null)
}

val appTabRowScreens = listOf(MainFeed, Collections, MyProfile)