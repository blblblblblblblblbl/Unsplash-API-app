package com.blblblbl.myapplication.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.presentation.view.compose_utils.ErrorItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingView
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.CollectionsFragmentViewModel
import com.blblblbl.myapplication.domain.models.collections.PhotoCollection
import com.blblblbl.myapplication.presentation.view.compose_utils.PhotoCollectionsList
import com.blblblbl.myapplication.presentation.viewModel.UserFragmentViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionsFragment : Fragment() {
    private val viewModel: CollectionsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getCollections()
        return ComposeView(requireContext()).apply {
            setContent {
                UnsplashTheme() {
                    Surface() {
                        PhotoCollectionsList(
                            photoCollections = viewModel.pagedCollections,
                            onClick = {collection -> collectionOnClick(collection)}
                        )
                    }
                }
            }
        }
    }
    fun collectionOnClick(photoCollection: PhotoCollection){
        val bundle = bundleOf()
        bundle.putString(CollectionPhotoListFragment.COLLECTION_ID_KEY, photoCollection.id)
        findNavController().navigate(
            R.id.action_collectionsFragment_to_collectionPhotoListFragment,
            bundle
        )
    }
}

@Composable
fun CollectionsFragmentTab(){
    val viewModel : CollectionsFragmentViewModel = hiltViewModel<CollectionsFragmentViewModel>()
    viewModel.getCollections()
    Surface() {
        PhotoCollectionsList(
            photoCollections = viewModel.pagedCollections,
            onClick = {}
        )
    }
}