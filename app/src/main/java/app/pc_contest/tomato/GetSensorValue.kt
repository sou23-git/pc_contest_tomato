package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetSensorValue : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private var gyrSensor: Sensor? = null

    private lateinit var saveFile: SaveFile

    private var accValueX: Float = 0F
    private var accValueY: Float = 0F
    private var accValueZ: Float = 0F
    private var gyrValueX: Float = 0F
    private var gyrValueY: Float = 0F
    private var gyrValueZ: Float = 0F

    private lateinit var textTemp: TextView
    private lateinit var buttonTemp: Button
    private lateinit var buttonBack: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        gyrSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        saveFile = SaveFile(this)

        textTemp = findViewById(R.id.text_temp)
        buttonTemp = findViewById(R.id.button_throw)
        buttonBack = findViewById(R.id.buttonBack)

        buttonBack.setOnClickListener{
            finish()
        }
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
        val textCsv = mutableListOf("$timeSensorChanged", "$accValueX", "$accValueY", "$accValueZ", "$gyrValueX", "$gyrValueY", "$gyrValueZ")
        textTemp.text = textCsv.toString()
        saveFile.writeList(textCsv)

        buttonTemp.setOnClickListener {
            saveFile.writeCsv()
        }
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