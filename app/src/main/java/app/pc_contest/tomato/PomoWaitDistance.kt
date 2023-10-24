package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

class PomoWaitDistance : AppCompatActivity() {

    /*private val fileName = "log.csv"
    private val csvFilePath = this.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        ?.toString().plus("/").plus(fileName)*/

    private lateinit var textView: TextView

    private var TIMES_DEFAULT = 0
    private var TIMES = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_po)

        textView = findViewById(R.id.textView28)
        if(intent.hasExtra("TIMES_DEFAULT")) {
            TIMES_DEFAULT = intent.getIntExtra("TIMES_DEFAULT", 0)
        }
        if(intent.hasExtra("TIMES")) {
            TIMES = intent.getIntExtra("TIMES", 0)
        }

        val distance = calcDistance(this@PomoWaitDistance)
        textView.text = distance.toString()
        val handler = Handler()

        if(125 <= distance && distance < 225) {
            if(175 <= distance) {
                textView.text = "ナイスピッチ！いい調子！"
            } else {
                textView.text = "目標まであと少し！"
            }
            handler.postDelayed({
                if(TIMES == 0) {
                    val intentClear = Intent(this@PomoWaitDistance, PomoPageClearActivity::class.java)
                    intentClear.putExtra("TIMES_DEFAULT", TIMES_DEFAULT)
                    Log.d("PomoWait", "Clear!")
                    startActivity(intentClear)
                } else {
                    val intentRetry = Intent(this@PomoWaitDistance, PomoPage2Activity::class.java)
                    intentRetry.putExtra("TIMES", TIMES)
                    intentRetry.putExtra("TIMES_DEFAULT", TIMES_DEFAULT)
                    Log.d("PomoWait", "Retry")
                    startActivity(intentRetry) }
            }, 2000)
        }
        if(75 <= distance && distance < 125) {
            textView.text = "もうちょっと頑張ろう！\n罰として嫌がらせします！"
            handler.postDelayed( {
                val intentHair = Intent(this@PomoWaitDistance, StackHairService::class.java)
                startService(intentHair)
            }, 1000)

        }
        if(0 <= distance && distance < 75) {
            textView.text = "ちゃんと投げて！！！"
            handler.postDelayed({
                val intentActivity = Intent(this@PomoWaitDistance, LockActivity::class.java)
                intent.putExtra("TIMES_DEFAULT", TIMES_DEFAULT)
                startActivity(intentActivity)
                val intentDistance = Intent(this@PomoWaitDistance, StackService::class.java)
                startService(intentDistance)
            }, 1000)
        }
        if(225 <= distance) {
            textView.text = "投げすぎを検知しました。スマホに破損がないか確認してください。"
            handler.postDelayed({
                val intentHome = Intent(this@PomoWaitDistance, MainActivity::class.java)
                startActivity(intentHome)
            }, 5000)

        }

        //戻るボタン無効化設定
        onBackPressedDispatcher.addCallback(callback)
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }

    private fun calcDistance(context: Context): Double {
        //var distance = 0
        val csvFileDir = context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString().plus("/").plus("log.csv")
        val csvFile = File(csvFileDir)
        val dataList: MutableList<List<String>> = csvReader().readAll(csvFile).toMutableList()
        dataList.removeAt(0)

        var timeList: MutableList<Float> = mutableListOf()
        var accList: MutableList<Float> = mutableListOf()
        Log.d("calcDis", "created list")
        for(i in dataList.indices) {
            timeList += dataList[i][0].toFloat()
            accList += dataList[i][1].toFloat()
        }
        Log.d("calcDis", "separated List")
        val max = accList.max()
        val maxIndex = accList.indexOf(max)
        Log.d("calcDis", "got max value")
        println("max = $max")
        for (i in 0..10) {
            val deleteIndex = maxIndex - 5
            accList.removeAt(deleteIndex)
        }
        Log.d("calcDis", "removed maxValue")
        val max2 = accList.max()
        val max2Index = accList.indexOf(max2)
        Log.d("calcDis", "got 2nd maxValue")
        println("max2 = $max2")
        val time = timeList[maxIndex] - timeList[max2Index]
        Log.d("calcDis", "calc time")
        println("time = $time")
        val dis = (-61.6574+9.17438*max2) + (1.282906*max) + (0.101236*time)
        println("distance = $dis")
        return dis
    }

}