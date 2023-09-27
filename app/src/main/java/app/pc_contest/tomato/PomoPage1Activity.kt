package app.pc_contest.tomato

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class PomoPage1Activity : AppCompatActivity() {

    private lateinit var buttonStart: ImageButton
    private lateinit var buttonHome : ImageButton

    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker
    private lateinit var secondPicker: NumberPicker

    private var timerTime: Array<Int?> = arrayOfNulls(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_1)

        buttonStart = findViewById(R.id.imageButton)
        buttonHome  = findViewById(R.id.imageButton2)

        hourPicker   = findViewById(R.id.hourPicker)
        minutePicker = findViewById(R.id.minutePicker)
        secondPicker = findViewById(R.id.secondPicker)

        hourPicker.maxValue = 23
        hourPicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.minValue = 0
        secondPicker.maxValue = 59
        secondPicker.minValue = 0

        buttonStart.setOnClickListener {
            timerTime[0] = hourPicker.value
            timerTime[1] = minutePicker.value
            timerTime[2] = secondPicker.value

            //ここでタイマーにtimerTimeを渡す

            val intentToPage2 = Intent(this, PomoPage2Activity::class.java)
            startActivity(intentToPage2)
        }
        buttonHome.setOnClickListener {
            finish()
        }
    }

}