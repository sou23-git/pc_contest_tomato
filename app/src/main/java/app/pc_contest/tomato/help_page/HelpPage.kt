@file:Suppress("DEPRECATION")

package app.pc_contest.tomato.help_page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HelpPage(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {

    override fun getItem(i: Int): Fragment {
        return when (i) {
            0 -> HelpPage1()
            1 -> HelpPage2()
            2 -> HelpPage3()
            else -> HelpPage4()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "Page $position"
    }
}