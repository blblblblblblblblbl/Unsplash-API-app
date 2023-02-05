package com.blblblbl.myapplication.viewModel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.blblblbl.myapplication.DownloadWorker
import com.blblblbl.myapplication.data.data_classes.photo_detailed.DetailedPhotoInfo
import com.blblblbl.myapplication.domain.usecase.GetDetailedPhotoInfoUseCase
import com.blblblbl.myapplication.domain.usecase.LikeStateUseCase
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject


@HiltViewModel
class PhotoDetailedInfoFragmentViewModel @Inject constructor(
    private val getPhotosUseCase: GetDetailedPhotoInfoUseCase,
    private val likeStateUseCase: LikeStateUseCase,
    @ApplicationContext private val context: Context
):ViewModel() {
    private val _detailedPhotoInfo = MutableStateFlow<DetailedPhotoInfo?>(null)
    var status = MutableLiveData<Boolean?>()
    var intent :Intent? = null
    val detailedPhotoInfo = _detailedPhotoInfo.asStateFlow()
    fun getPhotoById(id:String){
        viewModelScope.launch {
            val response = getPhotosUseCase.execute(id)
            _detailedPhotoInfo.value = response
            Log.d("MyLog","single photo by id response:${response}")
        }
    }
    fun changeLike(id: String, bool:Boolean){
        viewModelScope.launch {
            if (bool){
                likeStateUseCase.like(id)
            }
            else{
                likeStateUseCase.unlike(id)
            }
        }
    }
    fun download(){
        val handler = CoroutineExceptionHandler { _, exception ->
            downloadWithWorkManager()
        }
        downloadStraight(handler)

    }
    private fun downloadWithWorkManager(){
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val downloadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                Data.Builder()
                    .putString(DownloadWorker.URL,detailedPhotoInfo.value?.urls?.raw)
                    .putString(DownloadWorker.ID,detailedPhotoInfo.value?.id)
                    .build())
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(context)
            .enqueue(downloadWorkRequest)
    }
    private fun downloadStraight(handler :CoroutineExceptionHandler){
        CoroutineScope(Dispatchers.IO).launch(handler) {
            detailedPhotoInfo.value?.id?.let { id->
                saveImage(
                    Glide.with(context)
                        .asBitmap()
                        .load(detailedPhotoInfo.value?.urls?.raw) // sample image
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                        .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                        .submit()
                        .get(), id
                )
            }
        }
    }
    private fun saveImage(image: Bitmap,name:String): String? {
        var savedImagePath: String? = null
        val imageFileName = "$name.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/YOUR_FOLDER_NAME"
        )
        Log.d("MyLog", "storageDir: $storageDir")
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            galleryAddPic(savedImagePath)
        }
        return savedImagePath
    }
    private fun galleryAddPic(imagePath: String?) {
        imagePath?.let { path ->
            val file = File(path)
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
            //val intent = Intent(Intent.ACTION_VIEW)
            intent = Intent(Intent.ACTION_VIEW)
            intent?.let {intent->
                intent.setDataAndType(path.toUri(), "image/*")
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            status.postValue(true)
        }
    }
    fun openGallery(){
        intent?.let {intent->
            startActivity(context,intent,null)
        }
    }

}