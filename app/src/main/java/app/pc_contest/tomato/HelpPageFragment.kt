package app.pc_contest.tomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class HelpPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewLayout = inflater.inflate(R.layout.help, container, false)

        val textView = viewLayout.findViewById<TextView>(R.id.textView)
        val value: String? = arguments?.getString(Constance.PAGE_TITLE, "")
        textView.text = if(value.isNullOrEmpty()) "No title" else value
        return viewLayout
    }
}