package com.blblblbl.profile.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.profile.R
import com.blblblbl.profile.domain.model.photo.Photo
import com.blblblbl.profile.presentation.UserFragmentViewModel

/*@AndroidEntryPoint
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


}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFragmentCompose(
    photoOnClick:(Photo)->Unit,
    logoutIntent: Intent
){
    val viewModel : UserFragmentViewModel = hiltViewModel<UserFragmentViewModel>()
    viewModel.getUserInfo()
    val privateInfoState by viewModel.privateUserInfo.collectAsState()
    val publicInfoState by viewModel.publicUserInfo.collectAsState()
    val pagedPhotos by viewModel.pagedPhotos.collectAsState()

    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            UserTopBar(onLogOutClicked = { openDialog.value = true })
        }
    ) {
        if (openDialog.value) {
            LogOutDialog(
                openDialog = openDialog,
                logoutOnClick = { viewModel.logout(
                    context,
                    logoutIntent
                )
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
                text = stringResource(id = R.string.user)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
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