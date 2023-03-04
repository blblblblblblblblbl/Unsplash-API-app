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
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photos.Photo
import com.blblblbl.myapplication.domain.models.public_user_info.PublicUserInfo
import com.blblblbl.myapplication.presentation.view.compose_utils.StatesUI
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.UserFragmentViewModel
import com.blblblbl.myapplication.domain.models.user_info.UserInfo
import com.blblblbl.myapplication.presentation.view.compose_utils.MeInfoScreen
import com.blblblbl.myapplication.presentation.viewModel.PhotosFragmentViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel: UserFragmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUserInfo()
        return ComposeView(requireContext()).apply {
            setContent {
                val privateInfoState by viewModel.privateUserInfo.collectAsState()
                val publicInfoState by viewModel.publicUserInfo.collectAsState()
                val pagedPhotos by viewModel.pagedPhotos.collectAsState()
                UnsplashTheme() {
                    val openDialog = remember { mutableStateOf(false) }
                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.surface,
                        topBar = {
                            UserTopBar(onLogOutClicked = { openDialog.value = true })
                        }
                    ) {
                        if (openDialog.value) {
                            LogOutDialog(
                                openDialog = openDialog,
                                logoutOnClick = { viewModel.logout()
                                })
                        }
                        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                            MeInfoScreen(
                                privateUserInfo = privateInfoState,
                                publicUserInfo = publicInfoState,
                                pagedPhotosFlow =  pagedPhotos,
                                { id, bool -> viewModel.changeLike(id,bool) },
                                {photo -> openDetailed(photo)}
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
            R.id.action_userFragment_to_photoDetailedInfoFragment4,
            bundle
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFragmentTab(
    photoOnClick:(Photo)->Unit
){
    val viewModel : UserFragmentViewModel = hiltViewModel<UserFragmentViewModel>()
    viewModel.getUserInfo()
    val privateInfoState by viewModel.privateUserInfo.collectAsState()
    val publicInfoState by viewModel.publicUserInfo.collectAsState()
    val pagedPhotos by viewModel.pagedPhotos.collectAsState()

    val openDialog = remember { mutableStateOf(false) }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            UserTopBar(onLogOutClicked = { openDialog.value = true })
        }
    ) {
        if (openDialog.value) {
            LogOutDialog(
                openDialog = openDialog,
                logoutOnClick = { viewModel.logout()
                })
        }
        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            MeInfoScreen(
                privateUserInfo = privateInfoState,
                publicUserInfo = publicInfoState,
                pagedPhotosFlow =  pagedPhotos,
                { id, bool -> viewModel.changeLike(id,bool) },
                {photo-> photoOnClick(photo)}
            )
        }
    }
}


@Composable
fun LogOutDialog(
    openDialog:MutableState<Boolean>,
    logoutOnClick:()->Unit
){
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = { Text(text = stringResource(id = R.string.action_confirmation)) },
        text = { Text(stringResource(id = R.string.logout_confirmation)) },
        confirmButton = {
            Button(
                onClick = {
                    logoutOnClick()
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
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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