package app.pc_contest.tomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.view.isVisible
import app.pc_contest.tomato.databinding.CountDownBinding
import app.pc_contest.tomato.Timer.Companion.secondAll as secondAll1

class Timer : AppCompatActivity() {

    private lateinit var binding: CountDownBinding

    companion object {
        private const val hour: Long = 0
        private const val minute: Long = 0
        private const val second: Long = 5
        private var secondAll: Long = hour * 3600 + minute * 60 + second
        private var COUNT_DOWN_MILLISECOND: Long = secondAll1 * 1000
        private const val INTERVAL_MILLISECOND: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //ViewBindingでlayoutを描画（旧findViewById）
        super.onCreate(savedInstanceState)
        binding = CountDownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timeTextView.text = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}"
        binding.stopButton.isVisible = false
        binding.startButton.setOnClickListener{
            binding.startButton.isVisible
        }

        binding.startButton.setOnClickListener {
            binding.startButton.isVisible = false
            binding.stopButton.isVisible = true
            val timer = object : CountDownTimer(secondAll * 1000, 1000) {
                override fun onFinish() {
                    binding.startButton.isVisible = true
                    secondAll = hour * 3600 + minute * 60 + second
                    binding.timeTextView.text = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}"
                }

                override fun onTick(millisUntilFinished: Long) {
                    secondAll--
                    binding.timeTextView.text = "${(secondAll / 3600).toString().padStart(2, '0')}:${((secondAll % 3600) / 60).toString().padStart(2, '0')}:${((secondAll % 3600) % 60).toString().padStart(2, '0')}"
                }
            }
            timer.start()

            binding.stopButton.setOnClickListener {
                binding.startButton.isVisible = true
                binding.stopButton.isVisible = false
                timer.cancel()
            }

            binding.resetButton.setOnClickListener {
                timer.cancel()
                binding.startButton.isVisible = true
                binding.stopButton.isVisible = false
                binding.timeTextView.text = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}"
                secondAll = hour * 3600 + minute * 60 + second
            }
        }
    }
}