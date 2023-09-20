package app.pc_contest.tomato

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val NUM_PAGES = 2

class HelpPageAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment {
        val fragment = HelpPageFragment()

        val args = Bundle()
        args.putString(Constance.PAGE_TITLE, "ページタイトル : " + (position + 1) + "ページ目")
        fragment.arguments = args
        return fragment
    }
}