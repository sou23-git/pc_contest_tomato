package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import java.util.Timer
import java.util.concurrent.TimeUnit

class PomoPage2Activity : AppCompatActivity() {

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View
    private lateinit var textTimer: TextView
    private lateinit var buttonHome: ImageButton
    private lateinit var imageTomato: ImageView
    //タイマー替わり
    private var time = 10
    private var repeatNum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_2)

        textTimer = findViewById(R.id.time_text_view)
        buttonHome = findViewById(R.id.imageButton2)
        //一時的にタイマー替わり
        imageTomato = findViewById(R.id.imageView3)
        textTimer.text = time.toString()

        repeatNum = intent.getIntExtra("TIME", 0)
    }

    override fun onResume() {
        super.onResume()

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
        imageTomato.setOnClickListener {
            time--
            textTimer.text = time.toString()
            if(time <= 0) {
                val intent = Intent(this, PomoPage3Activity::class.java)
                intent.putExtra("TIME", repeatNum)
                Log.d("Pomo2", "createdIntent")
                startActivity(intent)
            }
        }
    }
}