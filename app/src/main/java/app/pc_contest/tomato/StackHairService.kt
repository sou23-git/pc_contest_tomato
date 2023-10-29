package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.concurrent.TimeUnit

class StackHairService : Service() {

    private lateinit var newView: View
    private lateinit var windowManager: WindowManager

    var hms: String = "00:00:00"
    private val channelId = "service_timer"
    private lateinit var notification: Notification
    private lateinit var notificationManager: NotificationManager

    @SuppressLint("ResourceType")
    override fun onCreate() {
        super.onCreate()

        windowManager = applicationContext
            .getSystemService(WINDOW_SERVICE) as WindowManager

        val inflater = LayoutInflater.from(this)
        //generate view for inflate from layout file
        val nullParent: ViewGroup? = null
        newView = inflater.inflate(R.layout.hair, nullParent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        val timer = CounterClass(10 * 1000, 1 * 1000)
        timer.start()

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
                //stopSelf()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val name = "Stack"
        val descriptionText = "Stack service notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        //channel登録
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d("stackService", "created notification")
    }

    fun updateNotification() {
        val title = "Timer"
        val intentNotification = Intent(this@StackHairService, PomoPage1Activity::class.java)
        intentNotification.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this@StackHairService, 0, intentNotification, PendingIntent.FLAG_IMMUTABLE)

        notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.main_icon)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText(hms)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setDeleteIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        Log.d("stackService", "updateNotification")
    }

    inner class CounterClass(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onTick(millisUntilFinished: Long) {

            hms = String.format(
                "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(
                    millisUntilFinished
                ),
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                    TimeUnit.MILLISECONDS.toHours(
                        millisUntilFinished
                    )
                ),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        millisUntilFinished
                    )
                )
            )
            println(hms)
            val timerInfoIntent = Intent(CountdownTimerService.TIME_INFO)
            timerInfoIntent.putExtra("VALUE", hms)
            LocalBroadcastManager.getInstance(this@StackHairService)
                .sendBroadcast(timerInfoIntent)
            updateNotification()
            Log.d("sub", "onTick")
            if (hms.substring(0, 2) == "00" && hms.substring(3, 5) == "00" && hms.substring(6, 8) == "10") {
                val intentSensor = Intent(this@StackHairService, GetSensorService::class.java)
                startService(intentSensor)
                notificationManager.notify(0, notification)
                Log.d("sub", "sensorService started!")
            }
        }

        override fun onFinish() {
            val intent = Intent(this@StackHairService, PomoPage1Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Log.d("stackService", "timer finished")
            stopSelf()
        }
    }
}