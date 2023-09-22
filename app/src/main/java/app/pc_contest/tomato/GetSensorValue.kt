package app.pc_contest.tomato

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetSensorValue : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private var gyrSensor: Sensor? = null

    private lateinit var saveFile: SaveFile
    private val maxDataRows = 200

    private var cursorCsv: Int = 0

    private var accValueX: Float = 0F
    private var accValueY: Float = 0F
    private var accValueZ: Float = 0F
    private var gyrValueX: Float = 0F
    private var gyrValueY: Float = 0F
    private var gyrValueZ: Float = 0F

    lateinit var textTemp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        gyrSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        saveFile = SaveFile(this)

        textTemp = findViewById(R.id.text_temp)
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
        val textCsv = mutableListOf("$cursorCsv", "$timeSensorChanged", "$accValueX", "$accValueY", "$accValueZ", "$gyrValueX", "$gyrValueY", "$gyrValueZ")
        saveFile.saveCsv(textCsv)
        cursorCsv++
        cursorCsv %= maxDataRows
        textTemp.text = ("$cursorCsv")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, gyrSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}