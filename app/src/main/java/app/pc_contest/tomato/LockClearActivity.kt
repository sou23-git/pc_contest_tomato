package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class LockClearActivity : AppCompatActivity() {

    private lateinit var mPopupWindow: PopupWindow
    private lateinit var mPopupView  : View
    private lateinit var buttonHome  : ImageButton

    @SuppressLint("InflateParams")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_2)

        buttonHome = findViewById(R.id.imageButton2)

        mPopupWindow = PopupWindow(this)

        mPopupView = layoutInflater.inflate(R.layout.top_warng, null)
        mPopupWindow.contentView = mPopupView

        //レイアウト設定
        mPopupView.findViewById<View>(R.id.imageButton6).setOnClickListener{
            //ページ移動:終了ボタン(IB6)が押されたらMainActivityへ
            val intent = Intent(this@LockClearActivity, MainActivity::class.java)
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

        //disable backButton
        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}