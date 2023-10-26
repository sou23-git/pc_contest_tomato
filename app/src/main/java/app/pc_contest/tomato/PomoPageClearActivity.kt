package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PomoPageClearActivity : AppCompatActivity() {

    private lateinit var buttonToTop: ImageButton
    private lateinit var buttonHome : ImageButton
    private lateinit var textTimeAll: TextView

    private var timesDefault = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_clear)

        if(intent.hasExtra("TIMES_DEFAULT")) {
            Log.d("PomoClear", "received")
            timesDefault = intent.getIntExtra("TIMES_DEFAULT", 0)
        }

        buttonToTop = findViewById(R.id.imageButton3)
        buttonHome  = findViewById(R.id.imageButton2)
        textTimeAll = findViewById(R.id.textView8)

        val time = 30 * timesDefault
        println(time)
        if(time % 60 == 0) {
            textTimeAll.text = (time / 60).toString().padStart(2, '0').plus(":").plus("00").plus(":").plus("00")
        } else {
            textTimeAll.text =
                ((time / 60).toString().padStart(2, '0').plus(":").plus((time % 60).toString())
                    .padStart(2, '0').plus(":").plus("00"))
        }
        buttonToTop.setOnClickListener {
            val intentToHome = Intent(this, MainActivity::class.java)
            startActivity(intentToHome)
        }
        buttonHome.setOnClickListener {
            val intentToHome = Intent(this, MainActivity::class.java)
            startActivity(intentToHome)
        }
    }
}