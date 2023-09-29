package app.pc_contest.tomato

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CoroutineTimer(hour: Int, minute: Int, second: Int) {

    private var countDownJob: Job? = null

    var timeAll = (hour * 60 * 60) + (minute * 60) + second

    fun startCountDown() {
        if (countDownJob != null) {
            return
        }
        countDownJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(500)
                timeAll--
                print(timeAll)
        }
        }
        countDownJob?.start()
    }

    fun stopCountDown() {
        countDownJob?.cancel()
        countDownJob = null
    }
}