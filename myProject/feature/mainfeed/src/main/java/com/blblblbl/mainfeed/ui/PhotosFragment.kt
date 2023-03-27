package com.blblblbl.mainfeed.ui




import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.mainfeed.domain.model.photos.Photo
import com.blblblbl.mainfeed.presentation.PhotosFragmentViewModel

/*@AndroidEntryPoint
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
                                PhotoGridView(
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

}*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosFragmentCompose(
    onSearchClicked: () -> Unit,
    onPhotoClicked: (Photo) -> Unit
){
    val viewModel :PhotosFragmentViewModel = hiltViewModel<PhotosFragmentViewModel>()
    viewModel.getPhotosFeed()
    Scaffold(
        topBar = {
            PhotosTopBar(
                onSearchClicked = {
                    onSearchClicked()
                }
            )
        },
        content = {
            Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                PhotoGridView(
                    photos = viewModel.pagedPhotos,
                    onClick = {photo -> onPhotoClicked(photo)},
                    changeLike = { id, bool -> viewModel.changeLike(id,bool) } )
            }
        }
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
                text = "search photo"
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
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