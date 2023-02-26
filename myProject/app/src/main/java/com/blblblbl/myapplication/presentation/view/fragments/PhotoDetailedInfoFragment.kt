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
import androidx.compose.ui.platform.testTag
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
import com.blblblbl.myapplication.domain.models.photo_detailed.Location
import com.blblblbl.myapplication.presentation.view.compose_utils.PhotoDetailedScreen
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
