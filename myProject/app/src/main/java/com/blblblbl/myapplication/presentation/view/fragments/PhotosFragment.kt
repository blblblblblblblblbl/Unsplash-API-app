package com.blblblbl.myapplication.presentation.view.fragments




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.presentation.view.compose_utils.PhotoListView
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.PhotosFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment() {
    private val viewModel: PhotosFragmentViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getPhotosFeed()
        return ComposeView(requireContext()).apply {
            setContent {
                UnsplashTheme {
                    Scaffold(
                        topBar = {
                            PhotosTopBar(
                                onSearchClicked = {
                                    findNavController().navigate(
                                        R.id.action_photosFragment_to_searchFragment
                                    )
                                }
                            )
                        },
                        content = {
                            Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                                PhotoListView(
                                    photos = viewModel.pagedPhotos,
                                    onClick = {photo -> openDetailed(photo)},
                                    changeLike = { id, bool -> viewModel.changeLike(id,bool) } )
                            }
                        }
                    )
                }
            }
        }
    }

    fun openDetailed(photo:Photo){
        val bundle = bundleOf()
        bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
        findNavController().navigate(
            R.id.action_photosFragment_to_photoDetailedInfoFragment,
            bundle
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PhotosTopBar(
        onSearchClicked: () -> Unit
    ){
        TopAppBar(
            title = {
                Text(
                    text = "search photo",
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        )
    }










}