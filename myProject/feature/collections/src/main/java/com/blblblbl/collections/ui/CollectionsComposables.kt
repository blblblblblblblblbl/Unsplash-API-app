package com.blblblbl.collections.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.collections.R
import com.blblblbl.collections.domain.model.collection.PhotoCollection
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
fun PhotoCollectionsList(
    photoCollections: Flow<PagingData<PhotoCollection>>,
    onClick:(PhotoCollection)->Unit
) {
    val lazyCollectionsItems: LazyPagingItems<PhotoCollection> = photoCollections.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ){
        items(lazyCollectionsItems){ item->
            if (item != null) {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        onClick(item)
                    },
                    shape = MaterialTheme.shapes.large
                ) {
                    PhotoCollectionItem(photoCollection = item)
                }
            }
        }
    }

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }


    AnimatedVisibility (
        showButton,
        enter = slideInHorizontally( initialOffsetX = {fullWidth -> fullWidth }),
        exit = slideOutHorizontally( targetOffsetX = {fullWidth -> fullWidth })
    ) {
        Box(Modifier.fillMaxSize()) {
            val coroutineScope = rememberCoroutineScope()
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    //.navigationBarsPadding()
                    .padding(bottom = 8.dp, end = 8.dp),
                shape = CircleShape,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("Up!")
            }
        }
    }


    lazyCollectionsItems?.let { items->
        StatesUI(items = items)
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
    GlideImage(imageModel = { imageUrl }, modifier = Modifier.fillMaxSize())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    )
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)) {
        val textSizeTitle = 30.sp
        val textSizeTotalPhotos = 20.sp
        val textSizeName = 15.sp
        val textSizeUserName = 10.sp
        Text(text = "${photoCollection.totalPhotos} ${stringResource(id = R.string.collection_total_photos)}", fontSize = textSizeTotalPhotos, color = Color.White)
        Text(text = "${photoCollection.title}", fontSize = textSizeTitle, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val avatar:String? = photoCollection.user?.profileImage?.large
            GlideImage(imageModel = {avatar}, modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ))
            Column(Modifier.padding(start = 5.dp)) {
                Text(text = "${photoCollection.user?.name}", fontSize = textSizeName, color = Color.White)
                Text(text = "@${photoCollection.user?.username}", fontSize = textSizeUserName, color = Color.White)
            }
        }

    }
}

@Composable
fun StatesUI(items: LazyPagingItems<PhotoCollection>){
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