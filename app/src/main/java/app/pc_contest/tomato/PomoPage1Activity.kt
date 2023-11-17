package app.pc_contest.tomato

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class PomoPage1Activity : AppCompatActivity() {

    private lateinit var buttonStart: ImageButton
    private lateinit var buttonHome : ImageButton
    private lateinit var buttonHelp: ImageButton

    private lateinit var numPicker: NumberPicker

    private var times = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.po_1)

        buttonStart = findViewById(R.id.imageButton)
        buttonHome  = findViewById(R.id.imageButton2)
        buttonHelp  = findViewById(R.id.imageButton5)

        numPicker = findViewById(R.id.hourPicker)

        numPicker.textSize = 100F
        numPicker.maxValue = 99
        numPicker.minValue = 1



        onBackPressedDispatcher.addCallback(callback)
    }

    private val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }

    @SuppressLint("InflateParams")
    override fun onResume() {
        super.onResume()

        buttonStart.setOnClickListener {
            //numPicker
            times = numPicker.value

            //次ページへ
            val intent = Intent(this, PomoThrowFirst::class.java)
            intent.putExtra("TIMES", times)
            intent.putExtra("TIMES_DEFAULT", times)
            Log.d("pomo1", "toPomoThrow")
            startActivity(intent)
        }

        buttonHelp.setOnClickListener {
            Log.d("pomo1", "goto help page")
            val intentHelp = Intent(this, HelpPageActivity::class.java)
            startActivity(intentHelp)
        }

        buttonHome.setOnClickListener {
            iconAnimation(buttonHome)
        }
    }

    private fun iconAnimation(img: ImageButton) {
        Log.d("pomo1", "animation!")
        val animatorList: MutableList<Animator> = ArrayList()

        animatorList.add(ObjectAnimator.ofFloat(img, "translationX", 0f, 600f).setDuration(150))
        animatorList.add(ObjectAnimator.ofFloat(img, "translationX", -600f, 600f).setDuration(300))
        animatorList.add(ObjectAnimator.ofFloat(img, "translationX", -600f, 0f).setDuration(150))

        val set = AnimatorSet()
        set.playSequentially(animatorList)
        set.start()
    }
}