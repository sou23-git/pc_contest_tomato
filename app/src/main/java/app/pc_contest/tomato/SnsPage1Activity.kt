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
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class SnsPage1Activity : AppCompatActivity() {

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View
    private lateinit var buttonHome : ImageButton
    private lateinit var buttonStart: ImageButton

    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker

    private val TIME_PICKER_INTERVAL = 5
    private val TIME_PICKER_THRESHOLD_HOUR_ADD = 55
    private val TIME_PICKER_THRESHOLD_HOUR_REMOVE = -55

    private var hour = 0
    private var minute = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_1)

        buttonHome  = findViewById(R.id.imageButton2)
        buttonStart = findViewById(R.id.imageButton)

        hourPicker = findViewById(R.id.hourPicker)
        minutePicker = findViewById(R.id.minutePicker)

        hourPicker.maxValue = 23
        hourPicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.minValue = 0

        //5分刻みに変更
        /*minutePicker.setOnValueChangedListener(OnValueChangeListener { _, oldVal, newVal ->
            val `val`: Int = when ((oldVal - newVal) * TIME_PICKER_INTERVAL) {
                TIME_PICKER_THRESHOLD_HOUR_ADD -> 1
                TIME_PICKER_THRESHOLD_HOUR_REMOVE -> -1
                else -> return@OnValueChangeListener
            }
            minutePicker.value = minutePicker.value + `val`
        })*/

    }

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

        buttonStart.setOnClickListener {
            hour = hourPicker.value
            minute = minutePicker.value
            val intent = Intent(this, SnsPage2Activity::class.java)
            intent.putExtra("HOUR", hour)
            intent.putExtra("MINUTE", minute)
            startActivity(intent)
        }
    }
}