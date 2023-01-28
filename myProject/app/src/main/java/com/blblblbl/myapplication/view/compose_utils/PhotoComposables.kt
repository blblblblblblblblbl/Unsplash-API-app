package com.blblblbl.myapplication.view.compose_utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.view.PhotoDetailedInfoFragment
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow

@Composable
fun PhotoList(
    photos: Flow<PagingData<Photo>>,
    photoScreen: @Composable (photo:Photo) -> Unit
){
    val lazyPhotosItems: LazyPagingItems<Photo> = photos.collectAsLazyPagingItems()
    LazyColumn{
        items(lazyPhotosItems){item->
            item?.let { photoScreen(photo = it)}
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
fun PhotoScreen(
    photo: Photo,
    onClick: () -> Unit,
    likeOnClick: ()->Unit
){
    val textColor = Color.White
    val textSizeTotalLikes = 15.sp
    val textSizeName = 15.sp
    val textSizeUserName = 10.sp
    var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max)
        .padding(10.dp)
        .clickable { onClick() }) {
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
                        painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                        contentDescription = "like icon",
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            isLiked=!isLiked
                            likeOnClick()
                        }
                    )
                }
                else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                        contentDescription = "like icon",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            isLiked=!isLiked
                            likeOnClick()
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun StatesUI(items: LazyPagingItems<Photo>){
    items.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            loadState.append is LoadState.Loading -> {
                LoadingItem()
            }
            loadState.refresh is LoadState.Error -> {
                val e = items.loadState.refresh as LoadState.Error

                ErrorItem(
                    message = e.error.localizedMessage!!,
                    modifier = Modifier.fillMaxSize(),
                    onClickRetry = { retry() }
                )

            }
            loadState.append is LoadState.Error -> {
                val e = items.loadState.append as LoadState.Error

                ErrorItem(
                    message = e.error.localizedMessage!!,
                    onClickRetry = { retry() }
                )

            }
        }
    }
}