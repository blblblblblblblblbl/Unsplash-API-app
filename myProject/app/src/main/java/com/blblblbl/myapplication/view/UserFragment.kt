package com.blblblbl.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.data.data_classes.public_user_info.photos.Photo
import com.blblblbl.myapplication.data.data_classes.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.view.compose_utils.ErrorItem
import com.blblblbl.myapplication.view.compose_utils.LoadingItem
import com.blblblbl.myapplication.view.compose_utils.LoadingView
import com.blblblbl.myapplication.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.viewModel.UserFragmentViewModel
import com.example.example.UserInfo
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel:UserFragmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUserInfo()
        return ComposeView(requireContext()).apply {
            setContent {
                UnsplashTheme() {
                    val openDialog = remember { mutableStateOf(false) }
                    Scaffold(
                        topBar = {
                            UserTopBar(onLogOutClicked = { openDialog.value = true })
                        }
                    ) {
                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    openDialog.value = false
                                },
                                title = { Text(text = stringResource(id = R.string.action_confirmation)) },
                                text = { Text(stringResource(id = R.string.logout_confirmation)) },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            viewModel.logout()
                                            openDialog.value = false },
                                        content = {
                                            Text(stringResource(id = R.string.logout))
                                        }
                                    )
                                },
                                dismissButton = {
                                    Button(
                                        onClick = { openDialog.value = false }
                                    ) {
                                        Text(stringResource(id = R.string.cancel))
                                    }
                                }
                            )
                        }
                        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                            screen(privateUserInfo = viewModel.privateUserInfo, publicUserInfo = viewModel.publicUserInfo)
                        }
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserTopBar(
        onLogOutClicked: () -> Unit
    ){
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.user),
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = onLogOutClicked) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = stringResource(id = R.string.logout_icon_description)
                    )
                }
            }
        )
    }
    @Composable
    fun screen(privateUserInfo: StateFlow<UserInfo?>,publicUserInfo: StateFlow<PublicUserInfo?>){
        val privateInfoState = privateUserInfo.collectAsState().value
        val publicInfoState = publicUserInfo.collectAsState().value
        privateInfoState?.let {
            val lazyPhotosItems: LazyPagingItems<Photo> = viewModel.pagedPhotos.collectAsLazyPagingItems()
            LazyColumn{
                item { UserInfo(it,publicInfoState)                }
                items(lazyPhotosItems){item->
                    item?.let { PhotoScreen(photo = it)}
                }
            }
            StatesUI(items = lazyPhotosItems)
            /*Column() {
                UserInfo(it,publicInfoState)
                likedPhotosList(viewModel.pagedPhotos)
            }*/
        }
    }
    
    @Composable
    fun UserInfo(userInfo: UserInfo,publicUserInfo: PublicUserInfo?){
        Card(modifier = Modifier.padding(10.dp), shape = MaterialTheme.shapes.large) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val avatar:String? = publicUserInfo?.profileImage?.large
                avatar?.let { avatar->
                    GlideImage(imageModel = {avatar}, modifier = Modifier
                        .clip(CircleShape)
                        .size(128.dp)
                        .align(CenterHorizontally))
                }
                Log.d("MyLog","User:${userInfo}")
                Text(text = "${userInfo.firstName} ${userInfo.lastName}", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(CenterHorizontally))
                Text(text = "@${userInfo.username}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(CenterHorizontally))
                userInfo.bio?.let{bio->
                    Text(text = "${bio}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 15.dp, bottom = 15.dp))
                }
                userInfo.location?.let { location->
                    Row() {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = stringResource(id = R.string.location_icon_description)
                        )
                        Text(text = "${location}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                userInfo.email?.let { email->
                    Row() {
                        Icon(
                            Icons.Outlined.Mail,
                            contentDescription = stringResource(id = R.string.mail_icon_description)
                        )
                        Text(text = "${email}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                userInfo.downloads?.let {downloads->
                    Row() {
                        Icon(
                            Icons.Outlined.Download,
                            contentDescription = stringResource(id = R.string.download_icon_description)
                        )
                        Text(text = "${downloads}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }


    @Composable
    fun likedPhotosList(photos: Flow<PagingData<Photo>>){
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
            /*.clickable {
                val bundle = bundleOf()
                bundle.putString(PhotoDetailedInfoFragment.PHOTO_ID_KEY, photo.id)
                findNavController().navigate(
                    R.id.action_collectionPhotoListFragment_to_photoDetailedInfoFragment2,
                    bundle
                )
            }*/) {
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
                            contentDescription = stringResource(id = R.string.like_icon_description),
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
                            contentDescription = stringResource(id = R.string.like_icon_description),
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


}