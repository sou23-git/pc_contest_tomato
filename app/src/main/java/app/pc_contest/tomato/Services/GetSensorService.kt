package app.pc_contest.tomato.Services

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.app.Service
import android.content.Context
import android.os.Environment
import android.os.IBinder
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter

class GetSensorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private var gyrSensor: Sensor? = null

    private lateinit var saveListToFile: SaveListToFile

    private var accValueX: Float = 0F
    private var accValueY: Float = 0F
    private var accValueZ: Float = 0F
    private var gyrValueX: Float = 0F
    private var gyrValueY: Float = 0F
    private var gyrValueZ: Float = 0F

    override fun onCreate() {
        super.onCreate()
        Log.d("SensorService", "onCreate")

        saveListToFile = SaveListToFile(this)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        gyrSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("SensorService", "onBind")
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("SensorService", "GetSensorService started!")
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, gyrSensor, SensorManager.SENSOR_DELAY_UI)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        saveListToFile.writeCsv()
        sensorManager.unregisterListener(this)
        stopSelf()
        Log.d("SensorService", "Wrote csv!")
        Log.d("SensorService", "onDestroy")
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    override fun onSensorChanged(event: SensorEvent) {

        val timeSensorChanged = System.currentTimeMillis()

        if(event.sensor.type === Sensor.TYPE_LINEAR_ACCELERATION) {
            accValueX = event.values[0]
            accValueY = event.values[1]
            accValueZ = event.values[2]
        }
        if(event.sensor.type === Sensor.TYPE_GYROSCOPE) {
            gyrValueX = event.values[0]
            gyrValueY = event.values[1]
            gyrValueZ = event.values[2]
        }
        Log.d("Sensor", "sensorChanged")
        val textCsv = mutableListOf("$timeSensorChanged",
            "$accValueX", "$accValueY", "$accValueZ",
            "$gyrValueX", "$gyrValueY", "$gyrValueZ")

        saveListToFile.writeRow(textCsv)
        Log.d("Sensor", "wroteRows")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    inner class SaveListToFile(context: Context) {
        //private val maxDataRows = 200
        private val header = listOf("time", "x_acc", "y_acc", "z_acc", "x_gyr", "y_gyr", "z_gyr")
        private val fileName = "log.csv"
        private val csvFilePath: String =
            context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                ?.toString()
                .plus("/").plus(fileName)
        private val listText = mutableListOf(header)

        fun writeRow(csvText: List<String>) {
            /*if (listText.size <= maxDataRows) {
                listText.add(csvText)
            } else {
                listText.removeAt(1)
                listText.add(csvText)
            }*/
            listText.add(csvText)
        }

        fun writeCsv() {
            csvWriter().writeAll(listText, csvFilePath)
        }
    }
}