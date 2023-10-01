package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import app.pc_contest.tomato.PopupTest.PoNext
import app.pc_contest.tomato.PopupTest.PopupActivity

class PomoPage1Activity : AppCompatActivity() {

    private lateinit var coroutineTimerPomo: CoroutineTimerPomo

    private lateinit var buttonStart: ImageButton
    private lateinit var buttonHome: ImageButton
    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View



    private lateinit var numPicker: NumberPicker

    private var times = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_1)

        buttonStart = findViewById(R.id.imageButton)
        buttonHome  = findViewById(R.id.imageButton2)

        numPicker = findViewById(R.id.numPicker)

        numPicker.maxValue = 23
        numPicker.minValue = 0

        buttonStart.setOnClickListener {
            times = numPicker.value

            coroutineTimerPomo.startCountDown(times)

            val intentToPage2 = Intent(this, PomoPage2Activity::class.java)
            startActivity(intentToPage2)
        }
        buttonHome.setOnClickListener {
            val intentToPage2 = Intent(this, PopupActivity::class.java)
            startActivity(intentToPage2)
        }

        fun onResume() {
            super.onResume()

            mPopupWindow = PopupWindow(this)

            mPopupView = layoutInflater.inflate(R.layout.po_top_warng, null)
            mPopupWindow.contentView = mPopupView

            //レイアウト設定
            mPopupView.findViewById<View>(R.id.imageButton6).setOnClickListener{
                //ページ移動
                val intent = Intent(this, PoNext::class.java)
                startActivity(intent)
                //ポップアップ削除
                if (mPopupWindow.isShowing) {
                    mPopupWindow.dismiss()
                }
            }
            mPopupView.findViewById<View>(R.id.imageButton4).setOnClickListener {
                if(mPopupWindow.isShowing) {
                    mPopupWindow.dismiss()
                }
            }

            //背景設定
            mPopupWindow.setBackgroundDrawable(
                ResourcesCompat.getDrawable(resources,
                R.drawable.popup_background, null))

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

            buttonStart.setOnClickListener {
                displayPopup()
            }
        }



    }
    override fun onDestroy() {
        if (mPopupWindow.isShowing) {
            mPopupWindow.dismiss()
        }
        super.onDestroy()
    }



}

