package app.pc_contest.tomato

import android.annotation.SuppressLint
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
import app.pc_contest.tomato.Services.CountdownTimerService

class SnsPage2Activity : AppCompatActivity() {

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView: View
    private lateinit var buttonHome: ImageButton
    private lateinit var textTimer: TextView

    //For debug
    private lateinit var imageTimer: ImageView

    private var hour = 0
    private var minute = 0
    private var time = "00:00:00"

    private var receiver: TimeReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_2)

        receiver = TimeReceiver()

        buttonHome = findViewById(R.id.imageButton2)
        textTimer = findViewById(R.id.time_text_view)

        //For debug
        imageTimer = findViewById(R.id.imageView3)

        hour = intent.getIntExtra("HOUR", 0)
        minute = intent.getIntExtra("MINUTE", 0)

        time = hour.toString().padStart(2, '0').plus(":")
            .plus(minute.toString().padStart(2, '0')).plus(":")
            .plus(("00").padStart(2, '0'))
        textTimer.text = time

        //service killer
        onBackPressedDispatcher.addCallback(callback)

        //timer
        val intent = Intent(this, CountdownTimerService::class.java)
        intent.putExtra("TIME", (hour * 60 * 60) + (minute * 60))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        }else {
            startService(intent)
        }
    }

    //kill service & back to home
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intentStopService = Intent(this@SnsPage2Activity, CountdownTimerService::class.java)
            stopService(intentStopService)
            val intentStartMainActivity = Intent(this@SnsPage2Activity, MainActivity::class.java)
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

        mPopupView = layoutInflater.inflate(R.layout.sn_top_warning, null)
        mPopupWindow.contentView = mPopupView

        //For debug
        imageTimer.setOnClickListener {
            val intentStopService = Intent(this, CountdownTimerService::class.java)
            stopService(intentStopService)
            val intentSkip = Intent(this@SnsPage2Activity, SnsWaitDistance::class.java)
            intentSkip.putExtra("TIME_ALL",
                (hour.toString().padStart(2, '0').plus(":")
                    .plus(minute.toString().padStart(2, '0')).plus(":")
                    .plus(("00").padStart(2, '0'))))
            startActivity(intentSkip)
            Log.d("Sns2", "Stopped & Skipped")
        }

        //レイアウト設定
        mPopupView.findViewById<View>(R.id.imageButton6).setOnClickListener{
            //ページ移動:終了ボタン(IB6)が押されたらMainActivityへ
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //kill service
            val intentStopService = Intent(this@SnsPage2Activity, CountdownTimerService::class.java)
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
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.action == CountdownTimerService.TIME_INFO) {
                if(intent.hasExtra("VALUE")) {
                    val time = intent.getStringExtra("VALUE").toString()

                    textTimer.text = time
                    //10秒以下は赤字
                    if(time.substring(0, 2) == "00" && time.substring(3, 5) == "00" && time.substring(6, 8) == "10") {
                        textTimer.setTextColor(Color.RED)
                        Log.d("pomo2", "color changed")
                    }
                    if(time == "TimerEnd") {
                        val intentTemp = Intent(this@SnsPage2Activity, SnsWaitDistance::class.java)
                        intentTemp.putExtra("TIME_ALL",
                            (hour.toString().padStart(2, '0').plus(":")
                            .plus(minute.toString().padStart(2, '0')).plus(":")
                            .plus(("00").padStart(2, '0'))))
                        startActivity(intentTemp)
                    }
                }
            }
        }
    }
}