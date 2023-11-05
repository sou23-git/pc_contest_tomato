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
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PomoPage3Activity : AppCompatActivity() {

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View
    private lateinit var textTimes: TextView
    private lateinit var textTimer: TextView
    private lateinit var buttonHome: ImageButton
    private lateinit var textTitle: TextView

    //For debug
    private lateinit var imageTomato: ImageView

    private var leftTime: Int = 0
    private var timesDefault = 0

    private var receiver: TimeReceiver? = null
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_3)

        textTimer = findViewById(R.id.time_text_view)
        textTimes = findViewById(R.id.textView10)
        buttonHome = findViewById(R.id.imageButton2)
        textTitle = findViewById(R.id.textView3)

        //For debug
        imageTomato = findViewById(R.id.imageView3)

        receiver = TimeReceiver()

        onBackPressedDispatcher.addCallback(callback)

        if (intent.hasExtra("TIMES")) {
            leftTime = intent.getIntExtra("TIMES", 0)
        }
        if(intent.hasExtra("TIMES_DEFAULT")) {
            Log.d("Pomo3", "received")
            timesDefault = intent.getIntExtra("TIMES_DEFAULT", 0)
        }

        textTimer.text = "00:00:00"
        textTimes.text = leftTime.toString()

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
            if(leftTime <= 0) {
                Log.d("Pomo3", "End pomo timer")
                val intent = Intent(this, PomoPageClearActivity::class.java)
                intent.putExtra("TIMES_DEFAULT", timesDefault)
                startActivity(intent)
            }
            if(leftTime >= 1) {
                Log.d("Pomo3", "Restart pomo timer")
                val intent = Intent(this, CountdownTimerService::class.java)
                intent.putExtra("TYPE", "POMO_REST")
                intent.putExtra("TIME", 20) //5 min
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                }else {
                    startService(intent)
                }
            }
        }

    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intentStopService = Intent(this@PomoPage3Activity, CountdownTimerService::class.java)
            stopService(intentStopService)
            val intentStartMainActivity = Intent(this@PomoPage3Activity, PomoPage1Activity::class.java)
            startActivity(intentStartMainActivity)
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("InflateParams")
    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver!!, IntentFilter(CountdownTimerService.TIME_INFO))

        mPopupWindow = PopupWindow(this)

        mPopupView = layoutInflater.inflate(R.layout.po_top_warng, null)
        mPopupWindow.contentView = mPopupView

        //レイアウト設定
        mPopupView.findViewById<View>(R.id.imageButton6).setOnClickListener{
            //ページ移動:終了ボタン(IB6)が押されたらMainActivityへ
            val intent = Intent(this, PomoPage1Activity::class.java)
            startActivity(intent)
            //kill service
            val intentStopService = Intent(this@PomoPage3Activity, CountdownTimerService::class.java)
            stopService(intentStopService)
            //ポップアップ削除
            if (mPopupWindow.isShowing) {
                mPopupWindow.dismiss()
            }
        }
        mPopupView.findViewById<View>(R.id.imageButton4).setOnClickListener {
            //ページ移動はなし、ポップアップ削除
            if(mPopupWindow.isShowing) {
                mPopupWindow.dismiss()
            }
        }

        //For debug
        imageTomato.setOnClickListener {
            val intentStopService = Intent(this, CountdownTimerService::class.java)
            stopService(intentStopService)
            val intentSkipRest = Intent(this, PomoWaitDistance::class.java)
            intentSkipRest.putExtra("TIMES", leftTime)
            intentSkipRest.putExtra("TIMES_DEFAULT", timesDefault)
            Log.d("Pomo3", timesDefault.toString())
            startActivity(intentSkipRest)
            Log.d("Pomo3", "Stopped & Skipped")
        }

        //背景設定
        mPopupWindow.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.popup_background, null))
        // タップ時に他のViewでキャッチされないための設定
        mPopupWindow.isOutsideTouchable = true
        mPopupWindow.isFocusable = true
        // 表示サイズの設定 今回は幅300dp
        val width =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350f, resources.displayMetrics)
        mPopupWindow.setWindowLayoutMode(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        mPopupWindow.width = width.toInt()
        mPopupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT

        fun displayPopup() {
            mPopupWindow.showAtLocation(mPopupView, Gravity.CENTER, 0, 0)
        }
        buttonHome.setOnClickListener {
            displayPopup()
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver!!)
    }

    inner class TimeReceiver: BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.action == CountdownTimerService.TIME_INFO) {
                if(intent.hasExtra("VALUE")) {
                    if(intent.getStringExtra("VALUE") == "TimerEnd") {
                        val intentTemp = Intent(this@PomoPage3Activity, PomoWaitDistance::class.java)
                        intentTemp.putExtra("TIMES", leftTime)
                        intentTemp.putExtra("TIMES_DEFAULT", timesDefault)
                        startActivity(intentTemp)
                    } else {
                        val timerText = intent.getStringExtra("VALUE").toString().substring(3,8)
                        val timerSec = timerText.substring(3,5).toInt()
                        if(timerText.substring(0,2).toInt() == 0 && timerSec <= 10) {
                            textTitle.text = "投げろ！"
                            textTimer.textSize = 80F
                            textTimer.setTextColor(Color.RED)
                            textTimer.text = "$timerSec 秒"
                        } else {
                            textTimer.text = timerText
                        }
                    }
                }
            }
        }
    }
}