package com.blblblbl.collections.ui

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.collections.R
import com.blblblbl.collections.domain.model.photo.Photo
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PhotoListView(
    photos: Flow<PagingData<Photo>>,
    onClick:(Photo)->Unit,
    changeLike:(String,Boolean)->Unit
){
    val lazyPhotosItems: LazyPagingItems<Photo> = photos.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    LazyColumn(state = listState){
        items(lazyPhotosItems){item->
            item?.let { PhotoView(photo = it,onClick,changeLike) }
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


    lazyPhotosItems?.let {items->
        StatesUI(items = items) }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGridView(
    photos: Flow<PagingData<Photo>>,
    onClick:(Photo)->Unit,
    changeLike:(String,Boolean)->Unit
){
    val lazyPhotosItems: LazyPagingItems<Photo> = photos.collectAsLazyPagingItems()
    val listStaggeredState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = listStaggeredState,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(6.dp)){
        items(lazyPhotosItems){item->
            item?.let { PhotoViewGirdElement(photo = it,onClick,changeLike) }
        }
    }

    val showButton by remember {
        derivedStateOf {
            listStaggeredState.firstVisibleItemIndex > 0
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
                    .padding(bottom = 8.dp, end = 8.dp),
                shape = CircleShape,
                onClick = {
                    coroutineScope.launch {
                        listStaggeredState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("Up!")
            }
        }
    }


    lazyPhotosItems?.let {items->
        StatesUI(items = items) }
}

@Composable
fun PhotoView(
    photo: Photo,
    onClick:(Photo)->Unit,
    changeLike:(String,Boolean)->Unit
){
    val textColor = Color.White
    val textSizeTotalLikes = 15.sp
    val textSizeName = 15.sp
    val textSizeUserName = 10.sp
    var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
    BoxWithConstraints(
        modifier = Modifier
        .fillMaxWidth())
    {
        val width = maxWidth
        val height: Dp
        if (photo.height!=null&&photo.width!=null){
            height = (width.value * photo.height!! / photo.width!!).roundToInt().dp
        }
        else{
            height = width
        }
        Surface(
            modifier = Modifier
                //.fillMaxWidth()
                .width(width)
                .height(height)
                .padding(10.dp)
                .clickable { onClick(photo) },
            shape = MaterialTheme.shapes.large
        ) {
            val bitmap = BlurHashDecoder.decode(photo.blurHash,50,50)
            GlideImage(imageModel = {bitmap},Modifier.fillMaxSize())
            GlideImage(imageModel = {photo.urls?.regular},Modifier.fillMaxSize())
            Box(modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.BottomCenter)) {
                    val avatar:String? = photo.user?.profileImage?.large
                    GlideImage(imageModel = {avatar}, modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ))
                    Column(Modifier.padding(start = 5.dp)) {
                        Text(text = "${photo.user?.name}", color = textColor, fontSize = textSizeName,modifier = Modifier.testTag("name"))
                        Text(modifier = Modifier.testTag("username"),text = "@${photo.user?.username}", color = textColor, fontSize = textSizeUserName)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(modifier = Modifier.testTag("likes"),text = "${photo.likes}", color = textColor, fontSize = textSizeTotalLikes, textAlign = TextAlign.End)
                    LikeButton(
                        state = isLiked,
                        onClick = {
                            isLiked = !isLiked
                            photo.id?.let { changeLike(it, isLiked)
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun PhotoViewGirdElement(
    photo: Photo,
    onClick:(Photo)->Unit,
    changeLike:(String,Boolean)->Unit
){
    val textColor = Color.White
    val textSizeName = 15.sp
    val textSizeUserName = 10.sp
    var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth())
    {
        val width = maxWidth
        val height: Dp
        if (photo.height!=null&&photo.width!=null){
            height = (width.value * photo.height!! / photo.width!!).roundToInt().dp
        }
        else{
            height = width
        }
        Surface(
            modifier = Modifier
                //.fillMaxWidth()
                .width(width)
                .height(height)
                .clickable { onClick(photo) },
            shape = MaterialTheme.shapes.large
        ) {
            val bitmap = BlurHashDecoder.decode(photo.blurHash,50,50)
            GlideImage(imageModel = {bitmap},Modifier.fillMaxSize())
            GlideImage(imageModel = {photo.urls?.small},Modifier.fillMaxSize())
            Box(modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.TopCenter)) {
                    Spacer(modifier = Modifier.weight(1f))
                    LikeButton(
                        state = isLiked,
                        onClick = {
                            isLiked = !isLiked
                            photo.id?.let { changeLike(it, isLiked)
                            }
                        }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.BottomStart)) {
                    val avatar:String? = photo.user?.profileImage?.large
                    GlideImage(imageModel = {avatar}, modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ))
                    Column(Modifier.padding(start = 5.dp)) {
                        Text(text = "${photo.user?.name}", color = textColor, fontSize = textSizeName,modifier = Modifier.testTag("name"))
                        Text(modifier = Modifier.testTag("username"),text = "@${photo.user?.username}", color = textColor, fontSize = textSizeUserName)
                    }
                }
            }
        }
    }
}
@Composable
fun LikeButton(
    state:Boolean,
    onClick:()->Unit
){
    val icon: ImageVector
    val tint: Color
    if(state){
        icon = Icons.Default.Favorite
        tint = Color.Red
    }
    else{
        icon = Icons.Default.FavoriteBorder
        tint = Color.White
    }
    IconButton(onClick = { onClick() }) {
        Icon(
            icon,
            contentDescription = "like icon",
            tint = tint,
            modifier = Modifier
                .testTag("likeIcon")
        )
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

@Preview
@Composable
fun PhotoViewPreview(
){
    val photo = Photo()
    photo.likes = 200
    photo.user?.name = "first last"
    photo.user?.username = " username"
    photo.user?.profileImage?.large = "https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"
    photo.urls?.regular = "https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=1080&fit=max"
    PhotoView(photo = photo, onClick = {}, changeLike = {s,b->{}})
}

// staggered variant. it works but have problems because it always resizes
@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}
@OptIn(ExperimentalFoundationApi::class)
public fun <T : Any> LazyStaggeredGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyStaggeredGridScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun StagGridPrev(){
    val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img2,
        R.drawable.img1,
        R.drawable.img4,
        R.drawable.img3,
        R.drawable.img1,
        R.drawable.img1,
        R.drawable.img4,
        R.drawable.img4,
        R.drawable.img2,
        R.drawable.img3)
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2) ){
        items(images) { item->
            Image(
                painterResource(item),
                contentDescription = ""
            )
        }
    }

}