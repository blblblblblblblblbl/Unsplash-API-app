package com.blblblbl.search.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.search.domain.model.photo.Photo
import com.blblblbl.search.presentation.SearchFragmentViewModel

// TODO: make it work or delete
/*@AndroidEntryPoint
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
                        containerColor = MaterialTheme.colorScheme.surface,
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

}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFragmentCompose(
    closeOnClick: ()->Unit,
    photoOnClick: (Photo)->Unit
){
    val viewModel: SearchFragmentViewModel = hiltViewModel()
    val searchQuery by viewModel.searchQuery
    val searchedImages = viewModel.searchedImages.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
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
                    closeOnClick()
                }
            )
        },
        content = {
            searchedImages.value?.let { imgFlow->
                Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                    PhotoListView(
                        photos = imgFlow,
                        {photo -> photoOnClick(photo)},
                        { id, bool -> viewModel.changeLike(id,bool) }
                    )
                }
            }
        }
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
            }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary, shape = CircleShape),
        color = MaterialTheme.colorScheme.primary,
        shape = CircleShape
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