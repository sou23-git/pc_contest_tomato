package app.pc_contest.tomato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.os.Bundle

class SnsPageClearActivity : AppCompatActivity() {
    private lateinit var buttonTop: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_clear)

        buttonTop = findViewById(R.id.imageButton3)
        buttonTop.setOnClickListener {
            val intentToTop = Intent(this, MainActivity::class.java)
            startActivity(intentToTop)
        }



    }
}