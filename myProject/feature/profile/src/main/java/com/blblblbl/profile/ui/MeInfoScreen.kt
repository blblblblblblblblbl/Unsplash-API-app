package com.blblblbl.profile.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.profile.R
import com.blblblbl.profile.domain.model.photo.Photo
import com.blblblbl.profile.domain.model.public_user_info.PublicUserInfo
import com.blblblbl.profile.domain.model.user_info.UserInfo
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun MeInfoScreen(
    privateUserInfo: UserInfo?,
    publicUserInfo: PublicUserInfo?,
    pagedPhotosFlow: Flow<PagingData<Photo>>?,
    changeLike:(String,Boolean)->Unit,
    onClick:(Photo)->Unit
){

    privateUserInfo?.let {privateInfo->
        val lazyPhotosItems: LazyPagingItems<Photo>? = pagedPhotosFlow?.collectAsLazyPagingItems()
        val listState = rememberLazyListState()
        LazyColumn(state = listState){
            item { UserInfoView(privateInfo,publicUserInfo)}

            lazyPhotosItems?.let {items->
                items(items){item->
                    item?.let { PhotoView(photo = it,onClick,changeLike)}
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
        lazyPhotosItems?.let {items->
            StatesUI(items = items) }

    }
}
@Composable
fun UserInfoView(
    userInfo: UserInfo,
    publicUserInfo: PublicUserInfo?){
    Card(modifier = Modifier.padding(10.dp), shape = MaterialTheme.shapes.large) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            val avatar:String? = publicUserInfo?.profileImage?.large
            avatar?.let { avatar->
                GlideImage(imageModel = {avatar}, modifier = Modifier
                    .clip(CircleShape)
                    .size(128.dp)
                    .align(Alignment.CenterHorizontally))
            }
            Text(text = "${userInfo.firstName} ${userInfo.lastName}", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(
                Alignment.CenterHorizontally
            ))
            Text(text = "@${userInfo.username}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(
                Alignment.CenterHorizontally
            ))
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