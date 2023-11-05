package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PomoThrowFirst: AppCompatActivity() {

    private lateinit var textTimer: TextView
    private lateinit var imageViewLeft: ImageView
    private lateinit var imageViewRight: ImageView
    private var receiver: TimeReceiver? = null

    private var times: Int = 0
    private var timesDefault: Int = 0

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pomo_first_throwing)

        Log.d("pomoThrow", "onCreate")

        textTimer = findViewById(R.id.time_text_view)
        imageViewLeft = findViewById(R.id.imageViewLeft)
        imageViewRight = findViewById(R.id.imageViewRight)
        textTimer.setTextColor(Color.RED)
        textTimer.text = "10 秒"

        receiver = TimeReceiver()

        times = intent.getIntExtra("TIMES", 0)
        timesDefault = intent.getIntExtra("TIMES_DEFAULT", 0)

        //service確認
        var needStartService = true
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (serviceInfo in manager.getRunningServices(Int.MAX_VALUE)) {
            if (CountdownTimerService::class.java.name == serviceInfo.service.className) {
                // 実行中なら起動しない
                needStartService = false
                Log.d("pomo2", "service has already running")
            }
        }
        if(needStartService) {
            //countdown
            val intentTimer = Intent(this@PomoThrowFirst, CountdownTimerService::class.java)
            intentTimer.putExtra("TIME", 10)
            intentTimer.putExtra("TYPE", "POMO_INITIAL_THROW")
            stopService(intentTimer)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intentTimer)
                Log.d("pomoThrow", "startServiceForeground")
            }else {
                startService(intentTimer)
                Log.d("pomoThrow", "startServiceNormal")
            }
        }

        imageViewLeft.setOnClickListener {
            val intent = Intent(this@PomoThrowFirst, PomoWaitDistance::class.java)
            intent.putExtra("SKIP", "")//デバッグボタンおしてSC
        }

        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }


    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver!!, IntentFilter(CountdownTimerService.TIME_INFO))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver!!)
    }



    inner class TimeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.action == CountdownTimerService.TIME_INFO) {
                if(intent.hasExtra("VALUE")) {
                    val time = intent.getStringExtra("VALUE")!!.substring(7,8).plus(" 秒")
                    if(intent.getStringExtra("VALUE") == "TimerEnd") {
                        val intentTemp = Intent(this@PomoThrowFirst, PomoWaitDistance::class.java)
                        intentTemp.putExtra("TIMES", times)
                        intentTemp.putExtra("TIMES_DEFAULT", timesDefault)
                        startActivity(intentTemp)
                    } else {
                        textTimer.text = time
                    }
                }
            }
        }
    }
}