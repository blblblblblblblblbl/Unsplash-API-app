package com.blblblbl.myapplication.presentation.view.compose_utils

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.models.photo_detailed.Location
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.StateFlow


@Composable
fun PhotoDetailedScreen(
    detailedPhotoInfo: StateFlow<DetailedPhotoInfo?>,
    changeLike:(String,Boolean)->Unit,
    isLocationShow:Boolean,
    locationAction:()->Unit,
    downloadAction:()->Unit,
    shareAction:()->Unit
){
    val state = detailedPhotoInfo.collectAsState().value
    state?.let {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            DetailedPhotoView(detailedPhotoInfo = it,changeLike)
            PhotoDescription(detailedPhotoInfo = it,isLocationShow,locationAction,downloadAction,shareAction)
        }
    }
}

@Composable
fun DetailedPhotoView(
    detailedPhotoInfo: DetailedPhotoInfo,
    changeLike:(String,Boolean)->Unit
){
    val textColor = Color.White
    val textSizeTotalLikes = 15.sp
    val textSizeName = 15.sp
    val textSizeUserName = 10.sp
    var isLiked by remember { mutableStateOf(detailedPhotoInfo.likedByUser?:false) }
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
                            detailedPhotoInfo.id?.let {changeLike(it,isLiked)  }
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
                            detailedPhotoInfo.id?.let {changeLike(it,isLiked)  }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun PhotoDescription(
    detailedPhotoInfo: DetailedPhotoInfo,
    isLocationShow:Boolean,
    locationAction:()->Unit,
    downloadAction:()->Unit,
    shareAction:()->Unit
){
    Column(modifier = Modifier.padding(10.dp)) {
        detailedPhotoInfo.location?.let { location->
            if (isLocationShow){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(modifier = Modifier.testTag("locationButton"),
                        onClick = {
                            locationAction
                        } ) {
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
        Row() {
            detailedPhotoInfo.exif?.let {exif->
                Column(modifier = Modifier.testTag("exifDescription")) {
                    exif.make?.let { make-> Text(text = "${stringResource(id = R.string.made_with_camera)}: ${make}",style = MaterialTheme.typography.bodyMedium) }
                    exif.model?.let {model-> Text(text = "${stringResource(id = R.string.camera_Model)}: ${model}",style = MaterialTheme.typography.bodyMedium) }
                    exif.exposureTime?.let {exposureTime-> Text(text = "${stringResource(id = R.string.exposure)}: ${exposureTime}",style = MaterialTheme.typography.bodyMedium) }
                    exif.aperture?.let {aperture-> Text(text = "${stringResource(id = R.string.aperture)}: ${aperture}",style = MaterialTheme.typography.bodyMedium) }
                    exif.focalLength?.let {focalLength-> Text(text = "${stringResource(id = R.string.focal_length)}: ${focalLength}",style = MaterialTheme.typography.bodyMedium) }
                    exif.iso?.let {iso-> Text(text = "${stringResource(id = R.string.iso)}: ${iso}",style = MaterialTheme.typography.bodyMedium) }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            detailedPhotoInfo.user?.bio?.let { bio->
                Column() {
                    Text(text = "${stringResource(id = R.string.about)} @${detailedPhotoInfo.user?.username}:",style = MaterialTheme.typography.bodyMedium)
                    Text(text = "${bio}:",style = MaterialTheme.typography.bodyMedium)
                }
            }

        }

        Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${stringResource(id = R.string.download)} (${detailedPhotoInfo.downloads})",style = MaterialTheme.typography.bodyMedium)
            IconButton(modifier = Modifier.testTag("downloadButton"),
                onClick = { downloadAction()
                }) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = "download icon"
                )
            }
            IconButton(modifier = Modifier.testTag("shareButton"),
                onClick = { shareAction()
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