package com.blblblbl.collections.ui


import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.presentation.CollectionsFragmentViewModel



@Composable
fun CollectionsFragmentCompose(
    collectionOnClick: (PhotoCollection) -> Unit
) {
    val viewModel: CollectionsFragmentViewModel = hiltViewModel<CollectionsFragmentViewModel>()
    viewModel.getCollections()
    Surface() {
        PhotoCollectionsList(
            photoCollections = viewModel.pagedCollections,
            onClick = { collection -> collectionOnClick(collection) }
        )
    }
}