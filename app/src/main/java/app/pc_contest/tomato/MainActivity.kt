package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import app.pc_contest.tomato.help_page.HelpPage

class MainActivity : FragmentActivity() {

    var viewPager: ViewPager? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_page)
        viewPager = findViewById<View>(R.id.pager) as ViewPager
        viewPager!!.adapter = HelpPage(
            supportFragmentManager
        )
    }
}