package app.pc_contest.tomato

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.concurrent.TimeUnit


class CountdownTimerService : Service() {

    val CHANNEL_ID = "notification_timer"
    var hms: String = "00:00:00"

    private var CD_TIME: Long = 0      //Change every mode
    private var CD_INTERVAL: Long = 0  //Default : 1 sec
    private var CD_TYPE: String? = null

    private lateinit var notification: Notification
    private lateinit var notificationManager: NotificationManager

    private var timer: CounterClass? = null
    private var pendingIntent: PendingIntent? = null
    private var intentNotification: Intent? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        //通知チャネル作成
        createNotificationChannel()
        Log.d("sub", "onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("sub", "onBind")
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        //Channel設定
        val name = "Timer"
        val descriptionText = "Timer service notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        //SystemにChannelを登録
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d("sub", "Channel registered")
    }

    fun updateNotification() {

        val title = "Timer"
        intentNotification = Intent(this@CountdownTimerService, PomoPage3Activity::class.java)
        intentNotification!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent
            .getActivity(this@CountdownTimerService, 0,
                intentNotification, PendingIntent.FLAG_MUTABLE)

        notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.main_icon)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText(hms)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setDeleteIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        Log.d("sub", "updateNotification")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(intent.hasExtra("TYPE")) {
            CD_TYPE = intent.getStringExtra("TYPE")
            Log.d("sub", CD_TYPE.toString())
            if(CD_TYPE == "POMO_TIMER") {
                //activityへのリンク
            }
        }

        if(intent.hasExtra("TIME")) {
            CD_TIME = (intent.getIntExtra("TIME", 10) * 1000).toLong()
        }
        CD_INTERVAL = 1 * 1000

        timer = CounterClass(CD_TIME, CD_INTERVAL)
        timer!!.start()
        Log.d("sub", "onStartCommand")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer!!.cancel()
        super.onDestroy()
        val timerInfoIntent = Intent(TIME_INFO)
        timerInfoIntent.putExtra("VALUE", "TimerEnd")
        println("TimerEnd")
        LocalBroadcastManager.getInstance(this@CountdownTimerService).sendBroadcast(timerInfoIntent)

        Log.d("sub", "onDestroy")
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
            val timerInfoIntent = Intent(TIME_INFO)
            timerInfoIntent.putExtra("VALUE", hms)
            LocalBroadcastManager.getInstance(this@CountdownTimerService)
                .sendBroadcast(timerInfoIntent)
            updateNotification()
            Log.d("sub", "onTick")
            if(hms.substring(0, 2) == "00" && hms.substring(3,5) == "00" && hms.substring(6, 8) == "10") {
                val intentSensor = Intent(this@CountdownTimerService, GetSensorService::class.java)
                startService(intentSensor)
                notificationManager.notify(0, notification)
                Log.d("sub", "sensorService started!")
            }
        }

        override fun onFinish() {
            Log.d("sub", "onFinish")
            val intent = Intent(this@CountdownTimerService, GetSensorService::class.java)
            stopService(intent)
            stopSelf()
        }
    }


    companion object {
        const val TIME_INFO = "time_info"
    }
}


//通知タップ時の画面遷移設定する！
