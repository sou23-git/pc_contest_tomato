package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonHome: ImageButton
    private lateinit var buttonPomo: ImageButton
    private lateinit var buttonSns: ImageButton
    private lateinit var buttonHelp: ImageButton
    private lateinit var buttonTemp: Button


    @SuppressLint("ServiceCast", "BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //contentViewのid紐づけ
        buttonHome = findViewById(R.id.imageButton2)
        buttonPomo = findViewById(R.id.imageButton7)
        buttonSns = findViewById(R.id.imageButton8)
        buttonHelp = findViewById(R.id.imageButton9)
        buttonTemp = findViewById(R.id.tempButton)

        val powerManager =
            applicationContext!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) //バッテリ最適化を無効化するダイアログ表示
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)

            //ボタンごとの画面遷移先設定
            buttonHome.setOnClickListener {

            }
            buttonPomo.setOnClickListener {
                val intent = Intent(this, PomoPage1Activity::class.java)
                startActivity(intent)
            }
            buttonSns.setOnClickListener {
                val intent = Intent(this, SnsPage1Activity::class.java)
                startActivity(intent)
            }
            buttonHelp.setOnClickListener {
                val intent = Intent(this, HelpPageActivity::class.java)
                startActivity(intent)
            }
            buttonTemp.setOnClickListener{//ここで一時的にホームにボタンを作ってGetsensorvalueに飛んでる
                val intentSenssorToAnswer = Intent(this,GetSensorValue::class.java)
                startActivity(intentSenssorToAnswer)
            }
        }
    }
}