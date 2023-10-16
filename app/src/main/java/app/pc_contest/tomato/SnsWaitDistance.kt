package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class SnsWaitDistance : AppCompatActivity() {

    /*private val fileName = "log.csv"
    private val csvFilePath = this.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        ?.toString().plus("/").plus(fileName)*/

    private lateinit var buttonNext: Button
    private lateinit var buttonWarn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_sn)

        buttonNext = findViewById(R.id.button)
        buttonWarn = findViewById(R.id.button2)
        val TIME_ALL = intent.getStringExtra("TIME_ALL")

        buttonNext.setOnClickListener {
            val intentClear = Intent(this@SnsWaitDistance, SnsPageClearActivity::class.java)
            intentClear.putExtra("TIME_ALL", TIME_ALL)
            Log.d("PomoWait", TIME_ALL.toString())
            startActivity(intentClear)
        }
        buttonWarn.setOnClickListener {
            //警告
        }

        //戻るボタン無効化設定
        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}
