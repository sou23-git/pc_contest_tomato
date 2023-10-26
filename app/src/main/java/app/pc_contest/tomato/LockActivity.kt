package app.pc_contest.tomato

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class LockActivity : AppCompatActivity() {

    private var receiver: TimeReceiver? = null

    private lateinit var textTimer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_1)

        receiver = TimeReceiver()

        textTimer = findViewById(R.id.textView13)

        onBackPressedDispatcher.addCallback(callback)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver!!, IntentFilter(CountdownTimerService.TIME_INFO))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver!!)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }

    inner class TimeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.action == CountdownTimerService.TIME_INFO) {
                if(intent.hasExtra("VALUE")) {
                    val time = intent.getStringExtra("VALUE")
                    if(time!!.substring(0, 2) == "00" && time.substring(3, 5) == "00" && time.substring(6, 8) == "10") {
                        textTimer.setTextColor(Color.RED)
                        Log.d("pomo2", "color changed")
                    }
                    textTimer.text = intent.getStringExtra("VALUE").toString()
                    if(intent.getStringExtra("VALUE") == "TimerEnd") {
                        val intentTemp = Intent(this@LockActivity, LockClearActivity::class.java)
                        startActivity(intentTemp)
                    }
                }
            }
        }
    }
}