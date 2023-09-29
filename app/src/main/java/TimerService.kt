package app.pc_contest.tomato

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.security.Provider.Service
import java.util.TimerTask


/*class TimerService : Service() {
    internal inner class TimerServiceBinder : Binder() {
        val service: TimerService
            get() = this@TimerService
    }

    private var timer: Timer? = null
    fun onCreate() {
        super.onCreate()
    }

    fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer.cancel()
            timer = null
        }
    }

    fun onBind(intent: Intent?): IBinder {
        return TimerServiceBinder()
    }

    fun onRebind(intent: Intent?) {}
    fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    fun schedule() {
        if (timer != null) {
            timer.cancel()
        }
        timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                sendBroadcast(Intent(ACTION))
            }
        }

        //int delay = 1000 * 60 * 60 * 24;
        //int delay = 1000 * 60;
        val delay = 1000
        val now = Date()
        now.setSeconds(0)
        timer.scheduleAtFixedRate(timerTask, now, delay)
    }

    companion object {
        const val ACTION = "TimerService"
    }
}
*/