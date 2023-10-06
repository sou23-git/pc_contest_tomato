package app.pc_contest.tomato

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.Intent.getIntent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.concurrent.TimeUnit


class CountdownTimerService(intent: Intent) : Service() {

    private  var times = 0

    val CHANNEL_ID = "sample"
    var hms: String = "00:00:00"

    val CD_TIME: Long = 10 * 1000 //ms
    val CD_INTERVAL: Long = 1000 //ms

    private var timer: CounterClass? = null
    override fun onCreate() {
        super.onCreate()
        Log.d("sub", "onCreate")
        println(CD_TIME)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("sub", "onBind")
        return null

    }

    fun startService(view: View?) {
        val intent = Intent(this, CountdownTimerService::class.java)
        startService(intent)
        Log.d("main", "startService")
    }

    fun stopService(view: View?) {
        val intent = Intent(this, CountdownTimerService::class.java)
        stopService(intent)
        Log.d("main", "stopService")
    }

    fun createNotification() {
        val notificationIntent = Intent(this, PomoPage2Activity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(hms)
            .setContentIntent(pendingIntent).build()
        startForeground(101, notification)
        Log.d("sub", "createNotification")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        times = intent.getIntExtra("TIMES", 0)
        timer = CounterClass(CD_TIME, CD_INTERVAL)
        timer!!.start()
        createNotification()
        Log.d("sub", "onStartCommand")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer!!.cancel()
        super.onDestroy()
        val timerInfoIntent = Intent(TIME_LEFT)
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
            val timerInfoIntent = Intent(TIME_LEFT)
            timerInfoIntent.putExtra("VALUE", hms)
            LocalBroadcastManager.getInstance(this@CountdownTimerService)
                .sendBroadcast(timerInfoIntent)
            createNotification()
            Log.d("sub", "onTick")
        }

        override fun onFinish() {
            val timerInfoIntent = Intent(TIME_LEFT)
            timerInfoIntent.putExtra("VALUE", "Completed")
            LocalBroadcastManager.getInstance(this@CountdownTimerService)
                .sendBroadcast(timerInfoIntent)
            Log.d("sub", "onFinish")
        }
    }

    companion object {
        const val TIME_LEFT = "time_left"
    }
}
