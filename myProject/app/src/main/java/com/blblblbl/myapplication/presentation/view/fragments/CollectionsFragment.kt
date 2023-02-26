package com.blblblbl.myapplication.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

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
                        PhotoCollectionsList(photoCollections = viewModel.pagedCollections)
                    }
                }
            }
        }
    }
    @Composable
    fun PhotoCollectionsList(photoCollections: Flow<PagingData<PhotoCollection>>) {
        val lazyPhotosItems: LazyPagingItems<PhotoCollection> = photoCollections.collectAsLazyPagingItems()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(lazyPhotosItems){item->
                if (item != null) {
                    Surface(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            val bundle = bundleOf()
                            bundle.putString(CollectionPhotoListFragment.COLLECTION_ID_KEY, item.id)
                            findNavController().navigate(
                                R.id.action_collectionsFragment_to_collectionPhotoListFragment,
                                bundle
                            )
                        },
                        shape = MaterialTheme.shapes.large
                    ) {
                        PhotoCollectionItem(photoCollection = item)
                    }
                }
            }
        }
        lazyPhotosItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }
                loadState.append is LoadState.Loading -> {
                    LoadingItem()
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyPhotosItems.loadState.refresh as LoadState.Error

                    ErrorItem(
                        message = e.error.localizedMessage!!,
                        modifier = Modifier.fillMaxSize(),
                        onClickRetry = { retry() }
                    )

                }
                loadState.append is LoadState.Error -> {
                    val e = lazyPhotosItems.loadState.append as LoadState.Error

                    ErrorItem(
                        message = e.error.localizedMessage!!,
                        onClickRetry = { retry() }
                    )

                }
            }
        }
    }
    @Preview
    @Composable
    fun ItemPreview(){
        var pc = PhotoCollection()
        pc.coverPhoto?.urls?.small = "https://images.unsplash.com/photo-1449614115178-cb924f730780?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=400&fit=max"
        pc.totalPhotos = 200
        pc.title = "Title"
        pc.user?.name = "firstname lastname"
        pc.user?.username = "username"
        pc.user?.profileImage?.large = "https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"
        PhotoCollectionItem(pc)
    }
    @Composable
    fun PhotoCollectionItem(photoCollection: PhotoCollection, modifier: Modifier = Modifier){
        val imageUrl:String? = photoCollection.coverPhoto?.urls?.small
        com.skydoves.landscapist.glide.GlideImage(imageModel = { imageUrl }, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 5.dp)) {
            val textColor = Color.White
            val textSizeTitle = 30.sp
            val textSizeTotalPhotos = 20.sp
            val textSizeName = 15.sp
            val textSizeUserName = 10.sp
            Text(text = "${photoCollection.totalPhotos} ${stringResource(id = R.string.collection_total_photos)}", color =textColor, fontSize = textSizeTotalPhotos)
            Text(text = "${photoCollection.title}", color = textColor, fontSize = textSizeTitle, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val avatar:String? = photoCollection.user?.profileImage?.large
                GlideImage(imageModel = {avatar}, modifier = Modifier.clip(CircleShape))
                Column(Modifier.padding(start = 5.dp)) {
                    Text(text = "${photoCollection.user?.name}", color = textColor, fontSize = textSizeName)
                    Text(text = "@${photoCollection.user?.username}", color = textColor, fontSize = textSizeUserName)
                }
            }

        }

    }


}