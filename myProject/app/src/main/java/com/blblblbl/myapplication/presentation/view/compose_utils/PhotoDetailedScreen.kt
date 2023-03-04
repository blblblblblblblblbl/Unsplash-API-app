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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.domain.models.photo_detailed.DetailedPhotoInfo
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt


@Composable
fun PhotoDetailedScreen(
    detailedPhotoInfo: DetailedPhotoInfo?,
    changeLike:(String,Boolean)->Unit,
    isLocationShow:Boolean,
    locationAction:()->Unit,
    downloadAction:()->Unit,
    shareAction:()->Unit
){
    detailedPhotoInfo?.let {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                DetailedPhotoView(detailedPhotoInfo = it)
                PhotoDescription(photo = it,isLocationShow,locationAction,downloadAction,shareAction,changeLike)
            }
        }
    }
}

@Composable
fun DetailedPhotoView(
    detailedPhotoInfo: DetailedPhotoInfo
){
    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp))
    {
        val width = maxWidth
        val height: Dp
        if (detailedPhotoInfo.height!=null&&detailedPhotoInfo.width!=null){
            height = (width.value * detailedPhotoInfo.height!! / detailedPhotoInfo.width!!).roundToInt().dp
        }
        else{
            height = width
        }
        Surface(
            modifier = Modifier
                .width(width)
                .height(height)
                .padding(10.dp)
        ) {
            val bitmap = BlurHashDecoder.decode(detailedPhotoInfo.blurHash,50,50)
            GlideImage(imageModel = {bitmap},Modifier.fillMaxSize())
            GlideImage(imageModel = {detailedPhotoInfo.urls?.regular},modifier = Modifier.fillMaxSize())
        }
    }
}
@Composable
fun PhotoDescription(
    photo: DetailedPhotoInfo,
    isLocationShow:Boolean,
    locationAction:()->Unit,
    downloadAction:()->Unit,
    shareAction:()->Unit,
    changeLike:(String,Boolean)->Unit
){
    val textStyle = MaterialTheme.typography.bodyMedium
    val textSizeTotalLikes = 15.sp
    val textSizeName = 15.sp
    val textSizeUserName = 10.sp
    Column(modifier = Modifier.padding(10.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            var isLiked by remember { mutableStateOf(photo.likedByUser?:false) }
            val avatar:String? = photo.user?.profileImage?.large
            GlideImage(imageModel = {avatar}, modifier = Modifier.clip(CircleShape))
            Column(Modifier.padding(start = 5.dp)) {
                Text(text = "${photo.user?.name}", fontSize = textSizeName)
                Text(text = "@${photo.user?.username}", fontSize = textSizeUserName)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${photo.likes}", fontSize = textSizeTotalLikes, textAlign = TextAlign.End)
            LikeButton(
                state = isLiked,
                onClick = {
                    isLiked = !isLiked
                    photo.id?.let { changeLike(it, isLiked)
                    }
                }
            )
        }


        photo.location?.let { location->
            if (isLocationShow){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LocationButton(locationAction)
                    Text(text = "${location.city?:""} ${location.country?:""}", style = textStyle)
                }
            }
        }
        var hashTags:String = ""
        photo.tags.forEach {
            hashTags += "#${it.title}"
        }
        if (hashTags!="") Text(text = hashTags, style = textStyle, modifier = Modifier.padding(20.dp))
        Row() {
            PhotoAbout(photo)
        }
        Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${stringResource(id = R.string.download)} (${photo.downloads})",style = textStyle)
            DownloadButton(downloadAction)
            ShareButton(shareAction)
        }
    }
}

@Composable
fun PhotoAbout(
    photo: DetailedPhotoInfo
){
    val textStyle = MaterialTheme.typography.bodyMedium
    Row() {
        photo.exif?.let { exif->
            Column(modifier = Modifier.testTag("exifDescription")) {
                exif.make?.let { make-> Text(text = "${stringResource(id = R.string.made_with_camera)}: $make",style = textStyle) }
                exif.model?.let {model-> Text(text = "${stringResource(id = R.string.camera_Model)}: $model",style = textStyle) }
                exif.exposureTime?.let {exposureTime-> Text(text = "${stringResource(id = R.string.exposure)}: $exposureTime",style = textStyle) }
                exif.aperture?.let {aperture-> Text(text = "${stringResource(id = R.string.aperture)}: $aperture",style = textStyle) }
                exif.focalLength?.let {focalLength-> Text(text = "${stringResource(id = R.string.focal_length)}: $focalLength",style = textStyle) }
                exif.iso?.let {iso-> Text(text = "${stringResource(id = R.string.iso)}: $iso",style = textStyle) }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        photo.user?.bio?.let { bio->
            Column() {
                Text(text = "${stringResource(id = R.string.about)} @${photo.user?.username}:",style = textStyle)
                Text(text = "${bio}:",style = textStyle)
            }
        }
    }
}

@Composable
fun LocationButton(
    onClick:()->Unit
){
    IconButton(modifier = Modifier.testTag("locationButton"),
        onClick = { onClick() } )
    {
        Icon(
            Icons.Outlined.LocationOn,
            contentDescription = "location icon",
        )
    }
}

@Composable
fun DownloadButton(
    onClick:()->Unit
){
    IconButton(modifier = Modifier.testTag("downloadButton"),
        onClick = { onClick() })
    {
        Icon(
            Icons.Default.Download,
            contentDescription = "download icon"
        )
    }
}
@Composable
fun ShareButton(
    onClick:()->Unit
){
    IconButton(modifier = Modifier.testTag("shareButton"),
        onClick = { onClick() })
    {
        Icon(
            Icons.Default.Share,
            contentDescription = "share icon"
        )
    }
}

@Preview
@Composable
fun IconPreviewDownload(){
    Icon(
        Icons.Default.Download,
        contentDescription = "share icon",
        tint = Color.Black)
}
@Preview
@Composable
fun IconPreviewShare(){
    Icon(
        Icons.Default.Share,
        contentDescription = "share icon",
        tint = Color.Black)
}
@Preview
@Composable
fun IconPreviewLocation(){
    Icon(
        Icons.Outlined.LocationOn,
        contentDescription = "share icon",
        tint = Color.Black)
}
@Preview
@Composable
fun IconPreviewFavourite(){
    Icon(
        Icons.Default.Favorite,
        contentDescription = "share icon",
        tint = Color.Black)
}
@Preview
@Composable
fun IconPreviewFavouriteOutlined(){
    Icon(
        Icons.Default.FavoriteBorder,
        contentDescription = "share icon",
        tint = Color.White)
}