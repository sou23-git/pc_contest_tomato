package app.pc_contest.tomato

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PomoPage2Activity : AppCompatActivity() {

    private lateinit var textTimer: TextView
    private lateinit var buttonHome: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_2)

        textTimer = findViewById(R.id.time_text_view)
        buttonHome = findViewById(R.id.imageButton2)

        buttonHome.setOnClickListener {
            //finish() ※戻れたらダメ！
        }
    }
}