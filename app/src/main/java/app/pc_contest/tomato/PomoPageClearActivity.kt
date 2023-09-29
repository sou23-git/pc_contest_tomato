package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PomoPageClearActivity : AppCompatActivity() {

    private lateinit var buttonToTop: ImageButton
    private lateinit var buttonHome : ImageButton
    private lateinit var textAllTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_clear)

        buttonToTop = findViewById(R.id.imageButton3)
        buttonHome  = findViewById(R.id.imageButton2)
        textAllTime = findViewById(R.id.textView8)

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