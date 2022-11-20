package com.thoriq.demoandroidnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NOTIFICATION_ID = 0
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "my_channel"
    }

    private lateinit var btnNotif: Button
    private lateinit var btnCamera: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNotif = findViewById(R.id.btn_send_notif)
        btnNotif.setOnClickListener{
            sendNotif()
        }
        btnCamera = findViewById(R.id.btn_move_camera)
        btnCamera.setOnClickListener{
            moveToCamera()
        }
    }

    private fun moveToCamera(){
        val itn = Intent(this, IntegrasiKamera::class.java)
        startActivity(itn)
    }

    private fun sendNotif(){
        val itn = Intent(this, Notified::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, itn, PendingIntent.FLAG_IMMUTABLE)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_notifications_24))
            .setContentTitle(resources.getString(R.string.content_title))
            .setContentText("Ciee baru bangun, pasti belum mandi")
            .setSubText("Mandi dulu gih")
            .setAutoCancel(true)

        /*
        Untuk Android Oreo Keatas
        */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME

            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notif = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notif)

    }
}