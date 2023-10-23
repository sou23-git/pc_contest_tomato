package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
    private lateinit var buttonHair: Button

    private var TIME_ALL = "00:00:00"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_sn)

        buttonNext = findViewById(R.id.button3)
        buttonWarn = findViewById(R.id.button)
        buttonHair = findViewById(R.id.button5)
        if(intent.hasExtra("TIME_ALL")) {
            TIME_ALL = intent.getStringExtra("TIME_ALL").toString()
        }

        buttonNext.setOnClickListener {
            val intentClear = Intent(this@SnsWaitDistance, SnsPageClearActivity::class.java)
            intentClear.putExtra("TIME_ALL", TIME_ALL)
            Log.d("PomoWait", "Clear!")
            startActivity(intentClear)
        }
        buttonWarn.setOnClickListener {
            val intentDistance = Intent(this@SnsWaitDistance, StackService::class.java)
            startService(intentDistance)
            val intentActivity = Intent(this@SnsWaitDistance, LockActivity::class.java)
            startActivity(intentActivity)
        }
        buttonHair.setOnClickListener {
            val intentHair = Intent(this@SnsWaitDistance, StackHairService::class.java)
            startService(intentHair)
        }

        //戻るボタン無効化設定
        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}
