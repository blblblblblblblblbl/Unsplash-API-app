package com.blblblbl.myapplication.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
import com.blblblbl.myapplication.presentation.viewModel.SearchFragmentViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchFragmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            val searchQuery by viewModel.searchQuery

            setContent {
                val searchedImages = viewModel.searchedImages.collectAsState()
                UnsplashTheme() {
                    Scaffold(
                        topBar = {
                            SearchWidget(
                                text = searchQuery,
                                onTextChange = {
                                    viewModel.updateSearchQuery(query = it)
                                },
                                onSearchClicked = {
                                    viewModel.search(query = it)
                                },
                                onCloseClicked = {
                                    findNavController().popBackStack()
                                }
                            )
                        },
                        content = {
                            searchedImages.value?.let { imgFlow->
                                Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                                    PhotoListView(
                                        photos = imgFlow,
                                        {photo -> openDetailed(photo)},
                                        { id, bool -> viewModel.changeLike(id,bool) }
                                    )
                                }
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
            R.id.action_searchFragment_to_photoDetailedInfoFragment3,
            bundle
        )
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchWidget(
        text: String,
        onTextChange: (String) -> Unit,
        onSearchClicked: (String) -> Unit,
        onCloseClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "SearchWidget"
                }.border(width = 2.dp, color = MaterialTheme.colorScheme.secondary ,shape = MaterialTheme.shapes.medium),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "TextField"
                    },
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(alpha = 0.5f),
                        text = "Search here..."
                    )
                },
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(alpha = 0.5f),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .semantics {
                                contentDescription = "CloseButton"
                            },
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                )
            )
        }
    }



}