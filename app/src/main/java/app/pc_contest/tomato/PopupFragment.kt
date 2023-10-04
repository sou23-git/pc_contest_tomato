package app.pc_contest.tomato

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.pc_contest.tomato.databinding.PoTopWarngBinding

class PopupFragment : Fragment(R.layout.po_top_warng) {

    private lateinit var binding: PoTopWarngBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.imageButton6.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_anim,
                    R.anim.enter_anim
                )
                .addToBackStack(null)
        }
    }
}