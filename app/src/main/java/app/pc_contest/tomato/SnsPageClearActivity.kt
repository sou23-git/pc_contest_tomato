package app.pc_contest.tomato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class SnsPageClearActivity : AppCompatActivity() {
    private lateinit var buttonTop: ImageButton
    private lateinit var buttonHome: ImageButton
    private lateinit var textTimeAll: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sn_clear)

        buttonTop = findViewById(R.id.imageButton3)
        buttonHome = findViewById(R.id.imageButton2)
        textTimeAll = findViewById(R.id.textView8)

        textTimeAll.text = intent.getStringExtra("TIME_ALL")

        buttonTop.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        onBackPressedDispatcher.addCallback(callback)
    }

    //back to Main
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(this@SnsPageClearActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}