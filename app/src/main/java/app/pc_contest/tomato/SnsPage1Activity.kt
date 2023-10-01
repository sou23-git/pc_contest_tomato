package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SnsPage1Activity : AppCompatActivity() {

    private lateinit var buttonHome : ImageButton
    private lateinit var buttonStart: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_1)

        buttonHome  = findViewById(R.id.imageButton2)
        buttonStart = findViewById(R.id.imageButton)

        buttonStart.setOnClickListener {
            val intentToPage2 = Intent(this, SnsPage2Activitiy::class.java)
            startActivity(intentToPage2)
        }


        buttonHome.setOnClickListener {
            finish()
        }
    }
}