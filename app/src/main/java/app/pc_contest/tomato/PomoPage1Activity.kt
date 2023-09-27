package app.pc_contest.tomato

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PomoPage1Activity : AppCompatActivity() {

    private lateinit var buttonHome: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_1)

        buttonHome = findViewById(R.id.imageButton2)

        buttonHome.setOnClickListener {
            finish()
        }
    }
}