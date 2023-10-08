package app.pc_contest.tomato

import android.app.Application

class ConstrainValues : Application() {

    private var pomoTime = 0   //times
    private var timerTime = 10 //time  [sec], Default = 25 * 60
    private var timerDelay = 1 //delay [sec], Default = 1


    //繰り返す回数設定
    fun getPomoTime(): Int {
        return pomoTime * 1000 //ms
    }
    fun setPomoTime(value: Int) {
        pomoTime = value
    }

    //タイマー時間設定
    fun getTimerTime(): Int {
        return timerTime * 1000 //ms
    }
    fun setTimerTime(value: Int) {
        timerTime = value
    }

    //タイマーカウントダウン間隔設定
    fun getTimerDelay(): Int {
        return timerDelay * 1000 //ms
    }
    fun setTimerDelay(value: Int) {
        timerDelay = value
    }
}