package com.blblblbl.myapplication.presentation.view.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.presentation.view.compose_utils.theming.UnsplashTheme
import com.blblblbl.myapplication.presentation.viewModel.PhotoDetailedInfoFragmentViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoDetailedInfoFragment : Fragment() {
    private val viewModel: PhotoDetailedInfoFragmentViewModel by viewModels()
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
                UnsplashTheme() {
                    all(detailedPhotoInfo = viewModel.detailedPhotoInfo)
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun all(detailedPhotoInfo: StateFlow<DetailedPhotoInfo?>){
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                if (it){
                    viewModel.status.value = null
                    coroutineScope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = "image saved",
                            actionLabel = "show in gallery"
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> TODO()
                            SnackbarResult.ActionPerformed -> TODO()//viewModel.openGallery()
                        }
                    }
                }
            }
        })
        Scaffold(
            content = {
                val a = it
                screen(detailedPhotoInfo = detailedPhotoInfo)
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }

        )
    }
    @Composable
    fun screen(detailedPhotoInfo: StateFlow<DetailedPhotoInfo?>){
        val state = detailedPhotoInfo.collectAsState().value
        state?.let {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                PhotoScreen(detailedPhotoInfo = it)
                PhotoDescription(detailedPhotoInfo = it)
            }
        }
    }
    @Composable
    fun PhotoScreen(detailedPhotoInfo: DetailedPhotoInfo){
        val textColor = Color.White
        val textSizeTotalLikes = 15.sp
        val textSizeName = 15.sp
        val textSizeUserName = 10.sp
        var isLiked by remember { mutableStateOf(detailedPhotoInfo.likedByUser?:false)}
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(10.dp)) {
            GlideImage(imageModel = {detailedPhotoInfo.urls?.regular},modifier = Modifier.fillMaxSize())
            Column() {
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val avatar:String? = detailedPhotoInfo.user?.profileImage?.large
                    GlideImage(imageModel = {avatar}, modifier = Modifier.clip(CircleShape))
                    Column(Modifier.padding(start = 5.dp)) {
                        Text(text = "${detailedPhotoInfo.user?.name}", color = textColor, fontSize = textSizeName)
                        Text(text = "@${detailedPhotoInfo.user?.username}", color = textColor, fontSize = textSizeUserName)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${detailedPhotoInfo.likes}", color = textColor, fontSize = textSizeTotalLikes, textAlign = TextAlign.End)
                    if (isLiked) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "like icon",
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                isLiked=!isLiked
                                detailedPhotoInfo.id?.let {viewModel.changeLike(it,isLiked)  }
                            }
                        )
                    }
                    else {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = "like icon",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                isLiked=!isLiked
                                detailedPhotoInfo.id?.let {viewModel.changeLike(it,isLiked)  }
                            }
                        )
                    }
                }
            }
        }
    }
    @Composable
    fun PhotoDescription(detailedPhotoInfo: DetailedPhotoInfo){
        Column(modifier = Modifier.padding(10.dp)) {
            detailedPhotoInfo.location?.let { location->
                val isLocName = location.city!=null|| location.country!=null
                val isLocNotNull = location.position?.latitude!=null&& location.position?.longitude!=null
                val isLocNotZero = location.position?.latitude!=0.0|| location.position?.longitude!=0.0
                if (isLocName||(isLocNotNull&&isLocNotZero)
                ){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            val latitude = location.position?.latitude
                            val longitude  = location.position?.longitude
                            if (latitude!=null &&longitude!=null){
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("geo:$latitude,$longitude")
                                )
                                startActivity(intent)
                            }
                        }) {
                            Icon(
                                Icons.Outlined.LocationOn,
                                contentDescription = "location icon",
                                )
                        }
                        Text(text = "${location.city?:""} ${location.country?:""}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            var hashTags:String = ""
            detailedPhotoInfo.tags.forEach {
                hashTags += "#${it.title}"
            }
            if (hashTags!="") Text(text = hashTags, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(20.dp))
            detailedPhotoInfo.exif?.let {exif->
                Row() {
                    Column() {
                        exif.make?.let { make-> Text(text = "${stringResource(id = R.string.made_with_camera)}: ${make}",style = MaterialTheme.typography.bodyMedium) }
                        exif.model?.let {model->Text(text = "${stringResource(id = R.string.camera_Model)}: ${model}",style = MaterialTheme.typography.bodyMedium)}
                        exif.exposureTime?.let {exposureTime->Text(text = "${stringResource(id = R.string.exposure)}: ${exposureTime}",style = MaterialTheme.typography.bodyMedium)}
                        exif.aperture?.let {aperture->Text(text = "${stringResource(id = R.string.aperture)}: ${aperture}",style = MaterialTheme.typography.bodyMedium)}
                        exif.focalLength?.let {focalLength->Text(text = "${stringResource(id = R.string.focal_length)}: ${focalLength}",style = MaterialTheme.typography.bodyMedium)}
                        exif.iso?.let {iso->Text(text = "${stringResource(id = R.string.iso)}: ${iso}",style = MaterialTheme.typography.bodyMedium)}
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    detailedPhotoInfo.user?.bio?.let { bio->
                        Column() {
                            Text(text = "${stringResource(id = R.string.about)} @${detailedPhotoInfo.user?.username}:",style = MaterialTheme.typography.bodyMedium)
                            Text(text = "${bio}:",style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                }
            }

            Row(modifier = Modifier.align(End), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${stringResource(id = R.string.download)} (${detailedPhotoInfo.downloads})",style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = { launcher.launch(REQUEST_PERMISSIONS)}) {
                    Icon(
                        Icons.Default.Download,
                        contentDescription = "download icon"
                    )
                }
                IconButton(onClick = {
                    ShareCompat.IntentBuilder(requireContext())
                        .setType("text/plain")
                        .setChooserTitle("Share URL")
                        .setText("https://unsplash.com/photos/${detailedPhotoInfo.id}")
                        .startChooser()
                }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "share icon"
                    )
                }
            }

        }
    }
    @Preview
    @Composable
    fun iconPreviewDownload(){
        Icon(
            Icons.Default.Download,
            contentDescription = "share icon",
            tint = Color.Black)
    }
    @Preview
    @Composable
    fun iconPreviewShare(){
        Icon(
            Icons.Default.Share,
            contentDescription = "share icon",
            tint = Color.Black)
    }
    @Preview
    @Composable
    fun iconPreviewLocation(){
        Icon(
            Icons.Outlined.LocationOn,
            contentDescription = "share icon",
            tint = Color.Black)
    }
    @Preview
    @Composable
    fun iconPreviewFavourite(){
        Icon(
            Icons.Default.Favorite,
            contentDescription = "share icon",
            tint = Color.Black)
    }
    @Preview
    @Composable
    fun iconPreviewFavouriteOutlined(){
        Icon(
            Icons.Default.FavoriteBorder,
            contentDescription = "share icon",
            tint = Color.White)
    }
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