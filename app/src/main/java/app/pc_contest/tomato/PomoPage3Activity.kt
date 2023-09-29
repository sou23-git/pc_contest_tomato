package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PomoPage3Activity : AppCompatActivity() {

    private var timerRest = 10   //5分タイマーに接続
    private var timesLeft = 0    //繰り返す回数残り

    private lateinit var textTimes: TextView
    private lateinit var textTimer: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_3)

        textTimer = findViewById(R.id.time_text_view)
        textTimes = findViewById(R.id.textView10)

        val buttonHome: ImageButton = findViewById(R.id.imageButton2)

        //update Values!



        buttonHome.setOnClickListener {
            timerRest--
            textTimer.text = timerRest.toString() //timerの返り値予定
            textTimes.text = timesLeft.toString()
            if(timerRest == 0 && timesLeft == 0) {
            val intentPomoClear = Intent(this, PomoPageClearActivity::class.java)
            startActivity(intentPomoClear)
            }
        }


    }
}