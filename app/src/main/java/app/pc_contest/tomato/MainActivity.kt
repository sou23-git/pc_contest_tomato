package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import app.pc_contest.tomato.help_page.HelpPage

import android.content.Intent
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageButton>(R.id.top_po_button) // レイアウト内のボタンのIDを指定

        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java) // 遷移先のアクティビティを指定
            startActivity(intent)
        }
    }
}