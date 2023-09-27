package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonHome: ImageButton
    private lateinit var buttonPomo: ImageButton
    private lateinit var buttonSns : ImageButton
    private lateinit var buttonHelp: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //contentViewのid紐づけ
        buttonHome = findViewById(R.id.imageButton2)
        buttonPomo = findViewById(R.id.imageButton7)
        buttonSns  = findViewById(R.id.imageButton8)
        buttonHelp = findViewById(R.id.imageButton9)

        //ボタンごとの画面遷移先設定
        buttonHome.setOnClickListener {
            val intentGetSensorValue = Intent(this, GetSensorValue::class.java)
            startActivity(intentGetSensorValue)
        }
        buttonPomo.setOnClickListener {
            val intentPomoPage1Activity = Intent(this, PomoPage1Activity::class.java)
            startActivity(intentPomoPage1Activity)
        }
        buttonSns.setOnClickListener {
            val intentSnsPage1Activity = Intent(this, SnsPage1Activity::class.java)
            startActivity(intentSnsPage1Activity)
        }
        buttonHelp.setOnClickListener {
            val intentHelpPageActivity = Intent(this, HelpPageActivity::class.java)
            startActivity(intentHelpPageActivity)
        }
    }
}