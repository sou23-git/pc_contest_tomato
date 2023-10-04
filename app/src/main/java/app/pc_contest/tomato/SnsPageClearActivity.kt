package app.pc_contest.tomato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.os.Bundle

class SnsPageClearActivity : AppCompatActivity() {
    private lateinit var buttonTop: ImageButton
    private lateinit var buttonHome: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_clear)

        buttonTop = findViewById(R.id.imageButton3)
        buttonHome = findViewById(R.id.imageButton2)
        buttonTop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}