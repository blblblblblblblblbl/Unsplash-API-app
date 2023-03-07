package com.blblblbl.detailedphoto.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.blblblbl.detailedphoto.domain.model.photo_detailed.Location
import com.blblblbl.detailedphoto.presentation.PhotoDetailedInfoFragmentViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoDetailedInfoFragment : Fragment() {
    // TODO: make it works or delete 
    /*private val viewModel: PhotoDetailedInfoFragmentViewModel by viewModels()
    private var photoId:String? = null

    val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){map->
        if (map.values.all { it }){
            Toast.makeText(context,"storage permissions granted", Toast.LENGTH_LONG).show()
            viewModel.download()
        }
        else {
            Toast.makeText(context,"storage permissions isn't granted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {photoId = it.getString(PHOTO_ID_KEY)  }
        photoId?.let { viewModel.getPhotoById(it)}
        return ComposeView(requireContext()).apply {
            setContent {
                val isLocationShow = viewModel.isToShowLocation.collectAsState()
                val detailedPhotoInfo by viewModel.detailedPhotoInfo. collectAsState()
                UnsplashTheme() {
                    PhotoDetailedScreen(
                        detailedPhotoInfo = detailedPhotoInfo,
                        { id, bool -> viewModel.changeLike(id,bool) } ,
                        isLocationShow.value,
                        { locationAction() },
                        { downloadAction() },
                        { shareAction() })
                }
            }
        }
    }
    fun locationAction(){
        val location = viewModel.detailedPhotoInfo.value?.location
        val latitude = location?.position?.latitude
        val longitude  = location?.position?.longitude
        if (latitude!=null &&longitude!=null){
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:$latitude,$longitude")
            )
            startActivity(intent)
        }
    }
    fun downloadAction(){
        launcher.launch(REQUEST_PERMISSIONS)
    }
    fun shareAction(){
        ShareCompat.IntentBuilder(requireContext())
            .setType("text/plain")
            .setChooserTitle("Share URL")
            .setText("https://unsplash.com/photos/${viewModel.detailedPhotoInfo.value?.id}")
            .startChooser()
    }*/

    companion object{
        const val PHOTO_ID_KEY = "photoIdKey"
        private val REQUEST_PERMISSIONS:Array<String> = buildList {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.P){
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoDetailedFragmentCompose(photoId:String){
    val viewModel: PhotoDetailedInfoFragmentViewModel = hiltViewModel()

    viewModel.getPhotoById(photoId)
    val isLocationShow = viewModel.isToShowLocation.collectAsState()
    val detailedPhotoInfo by viewModel.detailedPhotoInfo. collectAsState()
    val context = LocalContext.current

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )

    PhotoDetailedScreen(
        detailedPhotoInfo = detailedPhotoInfo,
        { id, bool -> viewModel.changeLike(id,bool) } ,
        isLocationShow.value,
        locationAction = {
            locationAction(context,viewModel.detailedPhotoInfo.value?.location) },
        downloadAction = {
            multiplePermissionsState.launchMultiplePermissionRequest()
            if (multiplePermissionsState.allPermissionsGranted) viewModel.download()
        },
        shareAction = { shareAction(context, photoId = photoId) })

}
fun shareAction(context: Context, photoId: String){
    ShareCompat.IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle("Share URL")
        .setText("https://unsplash.com/photos/${photoId}")
        .startChooser()
}

fun locationAction(context: Context,location: Location?){
    val latitude = location?.position?.latitude
    val longitude  = location?.position?.longitude
    if (latitude!=null &&longitude!=null){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude")
        )
        context.startActivity(intent)
    }
}
