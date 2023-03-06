package com.blblblbl.collections.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.collections.domain.model.photo.Photo
import com.blblblbl.collections.presentation.CollectionPhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class CollectionPhotoListFragment : Fragment() {
    // TODO: make it works or delete
    /*private var collectionId:String? = null
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
                        Surface() {
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
    }
    fun openDetailed(photo:Photo){
        val bundle = bundleOf()
        bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
        findNavController().navigate(
            R.id.action_collectionPhotoListFragment_to_photoDetailedInfoFragment2,
            bundle
        )
    }*/

    companion object{
        const val COLLECTION_ID_KEY = "collectionIdKey"
    }
}

@Composable
fun CollectionPhotoListFragmentCompose(
    collectionId:String?,
    photoOnClick: (Photo)->Unit
){
    val viewModel : CollectionPhotoListViewModel = hiltViewModel()
    viewModel.getCollectionPhotos(collectionId.toString())
    Surface() {
        val pagedPhotos = viewModel.pagedPhotos.collectAsState()
        Log.d("MyLog","CollectionPhotoListFragmentCompose recomposed")
        pagedPhotos.value?.let { imgFlow->
            PhotoListView(
                photos = imgFlow,
                {photo -> photoOnClick(photo)},
                { id, bool -> viewModel.changeLike(id,bool) }
            )
        }
    }


}