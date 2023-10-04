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

class SnsPage2Activity : AppCompatActivity() {

    //temp
    private lateinit var imageTomato: ImageView

    private val DEFAULT_MINUTE = 10

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView: View
    private lateinit var buttonHome: ImageButton
    private lateinit var textTimer: TextView
    private var hour = 0
    private var minute = 0
    private var time = "00 : 00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_2)

        //temp
        imageTomato = findViewById(R.id.imageView2)

        buttonHome = findViewById(R.id.imageButton2)
        textTimer = findViewById(R.id.time_text_view)
        hour = intent.getIntExtra("HOUR", 0)
        minute = intent.getIntExtra("MINUTE", 0)
        time = hour.toString().padStart(2, '0').plus(" : ").plus(minute.toString().padStart(2, '0'))
        textTimer.text = time
    }

    @SuppressLint("InflateParams")
    override fun onResume() {
        super.onResume()

        mPopupWindow = PopupWindow(this)

        mPopupView = layoutInflater.inflate(R.layout.sn_top_warning, null)
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
            if(hour >= 0 && minute > 0) {
                minute--
                time = hour.toString().padStart(2, '0').plus(" : ").plus(minute.toString().padStart(2, '0'))
                textTimer.text = time
            }
            if(hour > 0 && minute == 0) {
                hour--
                minute = DEFAULT_MINUTE
                time = hour.toString().padStart(2, '0').plus(" : ").plus(minute.toString().padStart(2, '0'))
                textTimer.text = time
            }
            if(hour == 0 && minute == 0) {
                val intent = Intent(this, SnsPageClearActivity::class.java)
                startActivity(intent)
            }
        }
    }
}