package com.blblblbl.collections.ui


import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.blblblbl.collections.presentation.CollectionsFragmentViewModel

/*@AndroidEntryPoint
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
                            onClick = { collection -> collectionOnClick(collection) }
                        )
                    }
                }
            }
        }
    }

    fun collectionOnClick(photoCollection: PhotoCollection) {
        val bundle = bundleOf()
        bundle.putString(CollectionPhotoListFragment.COLLECTION_ID_KEY, photoCollection.id)
        findNavController().navigate(
            R.id.action_collectionsFragment_to_collectionPhotoListFragment,
            bundle
        )
    }
}*/

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