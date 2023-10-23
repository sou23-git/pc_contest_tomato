package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import app.pc_contest.tomato.R

class StackHairService : Service() {

    private lateinit var newView: View
    private lateinit var windowManager: WindowManager

    @SuppressLint("ResourceType")
    override fun onCreate() {
        super.onCreate()

        windowManager = applicationContext
            .getSystemService(android.app.Service.WINDOW_SERVICE) as WindowManager

        val inflater = LayoutInflater.from(this)
        //generate view for inflate from layout file
        val nullParent: ViewGroup? = null
        newView = inflater.inflate(R.layout.hair, nullParent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val context = applicationContext
        val channelId = "default"
        val title = "StackView"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        //Notification Channel option
        val channel = NotificationChannel(
            channelId, title, NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(context, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.main_icon)
            .setContentText("APPLICATION_OVERLAY")
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .build()

        //startForeground
        startForeground(2, notification)

        val typeLayer = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            typeLayer, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        //get dp
        val dpScale = resources.displayMetrics.density.toInt()
        //put center
        params.gravity = Gravity.TOP or Gravity.END
        //more position settings
        params.x = 20 * dpScale
        params.y = 80 * dpScale


        //set TouchListener on View
        newView.setOnTouchListener { _, event ->
            Log.d("StackService", "onTouch")
            if(event.action == MotionEvent.ACTION_DOWN) {
                newView.performClick()
                //stop Service
                stopSelf()
            }
            false
        }

        //add view on display
        windowManager.addView(newView, params)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StackService", "onDestroy")
        //remove View
        windowManager.removeView(newView)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}