package com.blblblbl.myapplication.presentation.view.fragments




import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.presentation.viewModel.PhotosFragmentViewModel
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.data.repository.database.entities.DBPhoto
import com.blblblbl.myapplication.presentation.view.compose_utils.ErrorItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingItem
import com.blblblbl.myapplication.presentation.view.compose_utils.LoadingView
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class PhotosFragment : Fragment() {
    private val viewModel: PhotosFragmentViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                                PhotosList(photos = viewModel.pagedPhotos)
                            }
                        }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PhotosTopBar(
        onSearchClicked: () -> Unit
    ){
        TopAppBar(
            title = {
                Text(
                    text = "search photo",
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
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
    @Composable

    fun PhotosList(photos: Flow<PagingData<Photo>>) {
        val lazyPhotosItems: LazyPagingItems<Photo> = photos.collectAsLazyPagingItems()


        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2) ){
            items(
                items = lazyPhotosItems

            ) {item->
                if (item != null) {
                    PhotoItem(photo = item)
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

    @Composable
    fun PhotoItem(photo: Photo){
        val textColor = Color.White
        val textSizeTotalLikes = 15.sp
        val textSizeName = 15.sp
        val textSizeUserName = 10.sp
        var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable {
                val bundle = bundleOf()
                bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
                findNavController().navigate(
                    R.id.action_photosFragment_to_photoDetailedInfoFragment,
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
    @Preview
    @Composable
    fun PhotoItemPreview(){
        var photo = Photo()
        photo.likes = 200
        photo.user?.name = "first last"
        photo.user?.username = " username"
        photo.user?.profileImage?.large = "https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"
        photo.urls?.regular = "https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=1080&fit=max"
        val textColor = Color.White
        val textSizeTotalLikes = 15.sp
        val textSizeName = 15.sp
        val textSizeUserName = 10.sp
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)) {
            Image(
                painterResource(R.drawable.img1),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
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
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24), contentDescription = "like icon", tint = Color.White)
                }
            }
        }

    }


}