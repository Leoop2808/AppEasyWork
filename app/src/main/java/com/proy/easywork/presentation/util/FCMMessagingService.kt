package com.proy.easywork.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.proy.easywork.MainActivity
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection

class FCMMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {}
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        try {
            sendNotification(
                remoteMessage.data["title"], remoteMessage.data["body"]
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendNotification(titulo: String?, mensaje: String?) {
        val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
        val token: String = sp.getToken()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("token", token)
        intent.putExtra("push", true)
        createNotification(intent, titulo, mensaje)
    }

    private fun createNotification(intent: Intent, title: String?, message: String?) {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = "1000"
        val licon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)
        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(message)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            val mChannel =
                NotificationChannel(channelId, "Status", NotificationManager.IMPORTANCE_HIGH)
            //mChannel.description = "Acta de inspección previa"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.setSound(defaultSoundUri, att)
            mChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(mChannel)
        }
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(licon)
                .setContentTitle(title)
                .setStyle(bigText)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        notificationManager.notify(0, notificationBuilder.build())
    }
}