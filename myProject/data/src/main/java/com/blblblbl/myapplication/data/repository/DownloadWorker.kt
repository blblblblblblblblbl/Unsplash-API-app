package com.blblblbl.myapplication.data.repository

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blblblbl.myapplication.data.DownloadNotifications
import com.bumptech.glide.Glide
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted private val ctx: Context,
    @Assisted params: WorkerParameters,
    private val downloadNotifications: DownloadNotifications) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext
        downloadNotifications.makeStatusNotification("image will download when internet appear", appContext)
        val url = inputData.getString(URL)
        val id = inputData.getString(ID)

        return try {

            downloadNotifications.makeStatusNotification("downloading", appContext)
            if (url != null) {
                if (id != null) {
                    download(ctx,url,id)
                }
            }
            Result.success()
        } catch (throwable: Throwable) {

            Result.failure()
        }
    }
    private fun download(ctx: Context, url:String, id:String){
        CoroutineScope(Dispatchers.IO).launch {
            saveImage(
                Glide.with(ctx)
                    .asBitmap()
                    .load(url) // sample image
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                    .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                    .submit()
                    .get(), id
            )

        }
    }
    private fun saveImage(image: Bitmap, name:String): String? {
        var savedImagePath: String? = null
        val imageFileName = "$name.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/YOUR_FOLDER_NAME"
        )
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
            MediaScannerConnection.scanFile(ctx, arrayOf(file.toString()), null, null)
            val intent = Intent(Intent.ACTION_VIEW)
            intent?.let {intent->
                intent.setDataAndType(path.toUri(), "image/*")
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val pendingIntent: PendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                downloadNotifications.makeIntentNotification("image saved",pendingIntent, ctx)
            }
        }


    }

    companion object{
        const val URL:String = "imageurl"
        const val ID:String = "imageid"
    }
}


