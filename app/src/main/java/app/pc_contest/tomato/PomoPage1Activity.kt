package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class PomoPage1Activity : AppCompatActivity() {

    private lateinit var buttonStart: ImageButton
    private lateinit var buttonHome : ImageButton

    private lateinit var numPicker: NumberPicker

    private var times = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_1)

        buttonStart = findViewById(R.id.imageButton)
        buttonHome  = findViewById(R.id.imageButton2)

        numPicker = findViewById(R.id.numPicker)

        numPicker.maxValue = 99
        numPicker.minValue = 1

        buttonStart.setOnClickListener {
            times = numPicker.value

            //CountdownTimerService()
            val intentToPage2 = Intent(this, PomoPage2Activity::class.java)
            startActivity(intentToPage2)
        }
        buttonHome.setOnClickListener {
            finish()
        }
    }
}