package app.pc_contest.tomato

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager

class HelpActivity: FragmentActivity() {

    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_view)

        mPager = findViewById(R.id.pager)

        val pagerAdapter = HelpPageAdapter(supportFragmentManager)

        mPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if(mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }
}