package com.blblblbl.myapplication.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.presentation.view.compose_utils.ErrorItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingView
import com.blblblbl.myapplication.presentation.view.compose_utils.PhotoListView
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.CollectionPhotoListViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class CollectionPhotoListFragment : Fragment() {
    private var collectionId:String? = null
    private val viewModel: CollectionPhotoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {collectionId = it.getString(COLLECTION_ID_KEY) }
        viewModel.getCollectionPhotos(collectionId.toString())
        return ComposeView(requireContext()).apply {
            setContent {
                val pagedPhotos = viewModel.pagedPhotos.collectAsState()
                pagedPhotos.value?.let { imgFlow->
                    UnsplashTheme() {
                        PhotoListView(
                            photos = imgFlow,
                            {photo -> openDetailed(photo)},
                            { id, bool -> viewModel.changeLike(id,bool) }
                        )
                    }
                }

            }
        }
    }
    fun openDetailed(photo:Photo){
        val bundle = bundleOf()
        bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
        findNavController().navigate(
            R.id.action_collectionPhotoListFragment_to_photoDetailedInfoFragment2,
            bundle
        )
    }

    companion object{
        const val COLLECTION_ID_KEY = "collectionIdKey"
    }

}