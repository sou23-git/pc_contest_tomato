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

    private val channelIdNormal = "notification_timer"
    private val channelIdImportant = "notification_timer_for_warning"
    var hms: String = "00:00:00"

    private var cdTime: Long = 0      //Change every mode
    private var cdInterval: Long = 0  //Default : 1 sec
    private var cdType: String? = null

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
        //ポップアップ無しChannel設定
        val nameNormal = "Timer"
        val descriptionTextNormal = "Timer service notification"
        val importanceNormal = NotificationManager.IMPORTANCE_DEFAULT //NORMAL
        val channelNormal = NotificationChannel(channelIdNormal, nameNormal, importanceNormal).apply {
            description = descriptionTextNormal
        }
        val nameImportant = "Last Timer"
        val descriptionTextImportant = "Timer service notification with popup"
        val importanceImportant = NotificationManager.IMPORTANCE_HIGH //HIGH
        val channelImportant = NotificationChannel(channelIdImportant, nameImportant, importanceImportant).apply {
            description = descriptionTextImportant
        }
        //SystemにChannelを登録
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channelNormal)
        notificationManager.createNotificationChannel(channelImportant)
        Log.d("sub", "Channel registered")
    }

    fun updateNotification() {

        val title = "Timer"
        intentNotification = Intent(this@CountdownTimerService, PomoPage2Activity::class.java)
        intentNotification!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent
            .getActivity(this@CountdownTimerService, 0,
                intentNotification, PendingIntent.FLAG_MUTABLE)

        notification = NotificationCompat.Builder(applicationContext, channelIdNormal)
            .setSmallIcon(R.drawable.main_icon)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText(hms)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setDeleteIntent(pendingIntent)
            .build()
        notification.flags = Notification.FLAG_ONGOING_EVENT
        startForeground(1, notification)
        Log.d("sub", "updateNotification")
    }

    fun warningNotification() {
        val title = "Warning!"
        intentNotification = Intent(this@CountdownTimerService, PomoPage2Activity::class.java)
        intentNotification!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        pendingIntent = PendingIntent
            .getActivity(this@CountdownTimerService, 0,
                intentNotification, PendingIntent.FLAG_MUTABLE)

        notification = NotificationCompat.Builder(applicationContext, channelIdImportant)
            .setSmallIcon(R.drawable.main_icon)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText("$hms　　　スマホ投げて！！！！！")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setDeleteIntent(pendingIntent)
            .build()
        notification.flags = Notification.FLAG_ONGOING_EVENT
        startForeground(1, notification)
        Log.d("sub", "warningNotification")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(intent.hasExtra("TYPE")) {
            cdType = intent.getStringExtra("TYPE")
            Log.d("sub", cdType.toString())
            if(cdType == "POMO_TIMER") {
                Log.d("sub", "pomo timer")
            }
        }

        if(intent.hasExtra("TIME")) {
            cdTime = (intent.getIntExtra("TIME", 10) * 1000).toLong()
        }
        cdInterval = 1 * 1000

        timer = CounterClass(cdTime, cdInterval)
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
            Log.d("sub", "onTick")

            if(hms.substring(0,2) == "00" && hms.substring(3,5) == "00" && hms.substring(6,8).toInt() <= 10) {
                if(hms.substring(6,8).toInt() == 10) {
                    val intentSensor = Intent(this@CountdownTimerService, GetSensorService::class.java)
                    startService(intentSensor)
                    Log.d("sub", "sensorService started!")
                }
                warningNotification()
                Log.d("sub", "warning notification!")
            } else {
                //通常の通知
                updateNotification()
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
