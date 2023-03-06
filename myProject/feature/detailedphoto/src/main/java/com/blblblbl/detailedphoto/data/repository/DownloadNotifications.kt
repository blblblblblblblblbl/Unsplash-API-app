package com.blblblbl.detailedphoto.data.repository

import android.app.PendingIntent
import android.content.Context

interface DownloadNotifications {
    fun makeStatusNotification(message: String, context: Context)
    fun makeIntentNotification(message: String, intent: PendingIntent, context: Context)
}