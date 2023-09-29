package app.pc_contest.tomato.StackTest

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import app.pc_contest.tomato.R


class TestService : Service() {

    private lateinit var newView: View
    private lateinit var windowManager: WindowManager

    override fun onCreate() {
        super.onCreate()

        windowManager = applicationContext
            .getSystemService(WINDOW_SERVICE) as WindowManager

        // inflaterの生成
        val layoutInflater = LayoutInflater.from(this)

        // レイアウトファイルからInfalteするViewを作成
        val nullParent: ViewGroup? = null
        newView = layoutInflater.inflate(R.layout.po_top_warng, nullParent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?,
                                flags: Int, startId: Int): Int {

        val context = applicationContext
        val channelId = "default"
        val title: String = context.getString(R.string.app_name)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        // Notification　Channel 設定
        val channel = NotificationChannel(
            channelId, title, NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(context, channelId)
            .setContentTitle(title)
            // android標準アイコンから
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentText("APPLICATION_OVERLAY")
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .build()

        // startForeground
        startForeground(1, notification)

        val typeLayer = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            typeLayer, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        // dpを取得
        val dpScale = resources.displayMetrics.density.toInt()
        // 右上に配置
        params.gravity = Gravity.TOP or Gravity.END
        params.x = 20 * dpScale // 20dp
        params.y = 80 * dpScale // 80dp

        // ViewにTouchListenerを設定する
        newView.setOnTouchListener { _, event ->
            Log.d("debug", "onTouch")
            if (event.action == MotionEvent.ACTION_DOWN) {
                newView.performClick()

                // Serviceを停止
                stopSelf()
            }
            false
        }

        // Viewを画面上に追加
        windowManager.addView(newView, params)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("debug", "onDestroy")
        // Viewを削除
        windowManager.removeView(newView)
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}