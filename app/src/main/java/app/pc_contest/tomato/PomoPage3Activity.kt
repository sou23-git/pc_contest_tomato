package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class PomoPage3Activity : AppCompatActivity() {

    private val DEFAULT_TIMER_TIME = 10
    private var timerRest = DEFAULT_TIMER_TIME   //5分タイマーに接続
    // 繰り返す回数残り、前ActivityのIntentから
    private var timesLeft: Int = 0

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View
    private lateinit var textTimes: TextView
    private lateinit var textTimer: TextView
    private lateinit var buttonHome: ImageButton
    //temp
    private lateinit var imageTomato: ImageView

    private var constrainValues = ConstrainValues()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_3)

        val intentStop = Intent(this, CountdownTimerService::class.java)
        stopService(intentStop)

        textTimer = findViewById(R.id.time_text_view)
        textTimes = findViewById(R.id.textView10)
        buttonHome = findViewById(R.id.imageButton2)
        //temp
        imageTomato = findViewById(R.id.imageView3)

        timesLeft = constrainValues.getPomoTime()

        textTimer.text = timerRest.toString()
        textTimes.text = timesLeft.toString()

        var valueTemp = constrainValues.getPomoTime()
        constrainValues.setPomoTime(valueTemp--)

        //timerスタート
        val intent = Intent(this, CountdownTimerService::class.java)
        intent.putExtra("TYPE", "POMO_REST_TIMER")
        startService(intent)
    }

    override fun onResume() {
        super.onResume()

        //timer.timesLeftが0になれば次Activityへ

        imageTomato.setOnClickListener {
            if(timesLeft >= 0 && timerRest != 0) {
                timerRest--
                textTimer.text = timerRest.toString()
                if(timesLeft != 0 && timerRest == 0) {
                    timesLeft--
                    val intent = Intent(this, PomoPage2Activity::class.java)
                    intent.putExtra("TIME", timesLeft)
                    startActivity(intent)
                }
            }
            else {
                val intent = Intent(this, PomoPageClearActivity::class.java)
                startActivity(intent)
            }
        }

        mPopupWindow = PopupWindow(this)

        mPopupView = layoutInflater.inflate(R.layout.po_top_warng, null)
        mPopupWindow.contentView = mPopupView

        //レイアウト設定
        mPopupView.findViewById<View>(R.id.imageButton6).setOnClickListener{
            //ページ移動:終了ボタン(IB6)が押されたらMainActivityへ
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
}