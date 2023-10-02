package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import app.pc_contest.tomato.help_page.HelpFragment2
import app.pc_contest.tomato.help_page.HelpFragment1
import app.pc_contest.tomato.help_page.HelpFragment3
import app.pc_contest.tomato.help_page.HelpFragment4
import app.pc_contest.tomato.help_page.HelpPagerAdapter

class HelpPageActivity : FragmentActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var buttonHome: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_page)

        val flagmentList = listOf(
            HelpFragment1(),
            HelpFragment2(),
            HelpFragment3(),
            HelpFragment4())

        viewPager  = findViewById<View>(R.id.pager) as ViewPager
        buttonHome = findViewById(R.id.imageButton2)

        val adapter = HelpPagerAdapter(supportFragmentManager, flagmentList)
        viewPager.adapter = adapter

        buttonHome.setOnClickListener {
            finish()
        }
    }
}