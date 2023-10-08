package app.pc_contest.tomato

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
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.concurrent.TimeUnit


class CountdownTimerService : Service() {

    private val CHANNEL_ID = "test"
    var hms: String = "00:00:00"

    private var constrainValues = ConstrainValues()

    private var CD_TIME: Long = 0      //Change every mode
    private var CD_INTERVAL: Long = 0  //Default : 1 sec

    private lateinit var timerType: String


    private var timer: CounterClass? = null
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d("sub", "onCreate")
        println(CD_TIME)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("sub", "onBind")
        return null
    }


    fun createNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)

            .setSmallIcon(R.drawable.main_icon)
            .setContentTitle("Timer in Foreground !")
            .setAutoCancel(true)
            .setContentText(hms)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .build()
        startForeground(1, notification)
        Log.d("sub", "createNotification")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        createNotification()

        timerType = intent.getStringExtra("TYPE").toString()
        CD_TIME = constrainValues.getTimerTime().toLong()
        CD_INTERVAL = constrainValues.getTimerDelay().toLong()

        timer = CounterClass(CD_TIME, CD_INTERVAL)
        timer!!.start()
        Log.d("sub", "onStartCommand")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer!!.cancel()
        super.onDestroy()
        val timerInfoIntent = Intent(TIME_INFO)
        timerInfoIntent.putExtra("VALUE", "Stopped")
        LocalBroadcastManager.getInstance(this@CountdownTimerService).sendBroadcast(timerInfoIntent)
        Log.d("sub", "onDestroy")
    }

    inner class CounterClass(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
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
            createNotification()
            Log.d("sub", "onTick")
        }

        override fun onFinish() {
            changeActivity()
            Log.d("sub", "onFinish")
            stopSelf()
        }


    }

    private fun changeActivity() {
        Log.d("sub", "changeActivity")
        val value = constrainValues.getPomoTime()
        if(timerType == "POMO_TIMER" && value >= 1) {
            val intent = Intent(application.applicationContext, PomoPage3Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        else if(timerType == "POMO_REST_TIMER" && value >= 1) {
            val intent = Intent(application.applicationContext, PomoPage2Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        else{
            val intent = Intent(application.applicationContext, PomoPage3Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    companion object {
        const val TIME_INFO = "time_info"
    }
}
