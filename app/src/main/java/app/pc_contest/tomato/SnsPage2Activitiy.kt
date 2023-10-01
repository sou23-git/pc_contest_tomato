package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SnsPage2Activitiy : AppCompatActivity() {
    private lateinit var tempNext : ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_2)

        tempNext = findViewById(R.id.imageButton5)
        tempNext.setOnClickListener {
            val intentToPage3 = Intent(this, SnsPageClearActivity::class.java)
            startActivity(intentToPage3)
        }

    }
}