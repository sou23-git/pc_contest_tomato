package app.pc_contest.tomato

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CoroutineTimerPomo {

    private var countDownJob: Job? = null

    fun startCountDown(time: Int) {
        var times = time
        if (countDownJob != null) {
            return
        }
        countDownJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(500)
                times--
                print(times)
            }
        }
        countDownJob?.start()
    }

    fun stopCountDown() {
        countDownJob?.cancel()
        countDownJob = null
    }
}