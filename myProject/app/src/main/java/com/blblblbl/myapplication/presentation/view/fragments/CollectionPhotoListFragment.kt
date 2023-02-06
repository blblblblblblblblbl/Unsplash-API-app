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
import com.blblblbl.myapplication.domain.models.public_user_info.photos.Photo
import com.blblblbl.myapplication.presentation.view.compose_utils.ErrorItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingView
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
                UnsplashTheme() {
                    PhotoList(photos = viewModel.pagedPhotos)
                }
            }
        }
    }
    @Composable
    fun PhotoList(photos: Flow<PagingData<Photo>>){
        val lazyPhotosItems: LazyPagingItems<Photo> = photos.collectAsLazyPagingItems()
        LazyColumn{
            items(lazyPhotosItems){item->
                item?.let { PhotoScreen(photo = it)}
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
    @Composable
    fun PhotoScreen(photo: Photo){
        val textColor = Color.White
        val textSizeTotalLikes = 15.sp
        val textSizeName = 15.sp
        val textSizeUserName = 10.sp
        var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(10.dp)
            .clickable {
                val bundle = bundleOf()
                bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
                findNavController().navigate(
                    R.id.action_collectionPhotoListFragment_to_photoDetailedInfoFragment2,
                    bundle
                )
            }) {
            GlideImage(imageModel = {photo.urls?.regular},modifier = Modifier.fillMaxSize())
            Column() {
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val avatar:String? = photo.user?.profileImage?.large
                    GlideImage(imageModel = {avatar}, modifier = Modifier.clip(CircleShape))
                    Column(Modifier.padding(start = 5.dp)) {
                        Text(text = "${photo.user?.name}", color = textColor, fontSize = textSizeName)
                        Text(text = "@${photo.user?.username}", color = textColor, fontSize = textSizeUserName)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${photo.likes}", color = textColor, fontSize = textSizeTotalLikes, textAlign = TextAlign.End)
                    if (isLiked) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = "like icon",
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                isLiked=!isLiked
                                photo.id?.let {viewModel.changeLike(it,isLiked)  }
                            }
                        )
                    }
                    else {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = "like icon",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                isLiked=!isLiked
                                photo.id?.let {viewModel.changeLike(it,isLiked)  }
                            }
                        )
                    }
                }
            }
        }
    }

    companion object{
        const val COLLECTION_ID_KEY = "collectionIdKey"
    }

}