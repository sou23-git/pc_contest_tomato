package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import app.pc_contest.tomato.Services.StackService

class PomoWaitDistance : AppCompatActivity() {

    /*private val fileName = "log.csv"
    private val csvFilePath = this.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        ?.toString().plus("/").plus(fileName)*/

    private lateinit var buttonNext: Button
    private lateinit var buttonWarn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_po)

        buttonNext = findViewById(R.id.button)
        buttonWarn = findViewById(R.id.button2)
        val TIMES_DEFAULT = intent.getIntExtra("TIMES_DEFAULT", 0)

        buttonNext.setOnClickListener {
            val intentClear = Intent(this@PomoWaitDistance, PomoPageClearActivity::class.java)
            intentClear.putExtra("TIMES_DEFAULT", TIMES_DEFAULT)
            Log.d("PomoWait", TIMES_DEFAULT.toString())
            startActivity(intentClear)
        }
        buttonWarn.setOnClickListener {
            val intent = Intent(this@PomoWaitDistance, StackService::class.java)
            startService(intent)
        }

        //戻るボタン無効化設定
        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}