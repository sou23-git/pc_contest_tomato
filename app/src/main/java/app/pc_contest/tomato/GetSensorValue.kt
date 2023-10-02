package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class GetSensorValue : AppCompatActivity() {

    private lateinit var textTemp: TextView
    private lateinit var buttonTemp: Button
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        textTemp = findViewById(R.id.text_temp)
        buttonTemp = findViewById(R.id.button_throw)
        buttonBack = findViewById(R.id.buttonBack)

        buttonBack.setOnClickListener {
            finish()
        }

        buttonTemp.setOnClickListener {
            calculateValuesFromCSV()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateValuesFromCSV() {
        val csvFilePath = getExternalFilesDir(null)?.absolutePath + "/log.csv"
        val csvData = readCSVFile(csvFilePath)

        if (csvData.isNotEmpty()) {
            // ACCvalueX、ACCValueY、ACCvalueZの平均値を計算
            val averageValueX = calculateAverage(csvData.map { it[1].toFloat() })
            val averageValueY = calculateAverage(csvData.map { it[2].toFloat() })
            val averageValueZ = calculateAverage(csvData.map { it[3].toFloat() })

            // 平均値から最大値とそのインデックスを取得
            val (maxValue, maxIndex) = findMaxValueAndIndex(listOf(averageValueX, averageValueY, averageValueZ))

            // 最大値から離れた場所で2番目に大きい値とそのインデックスを取得
            val (secondMaxValue, secondMaxIndex) = findSecondMaxValueAndIndex(listOf(averageValueX, averageValueY, averageValueZ), maxIndex)

            // 結果を表示
            val resultText = """
                平均値:
                    X: $averageValueX
                    Y: $averageValueY
                    Z: $averageValueZ
                
                最大値:
                    Value: $maxValue
                    Index: $maxIndex
                
                最大値から離れた場所で2番目に大きい値:
                    Value: $secondMaxValue
                    Index: $secondMaxIndex
            """.trimIndent()

            textTemp.text = resultText
        } else {
            textTemp.text = "CSVファイルが空です。"
        }
    }

    private fun readCSVFile(filePath: String): List<List<String>> {
        val csvData = mutableListOf<List<String>>()

        try {
            val file = File(filePath)
            if (file.exists()) {
                val lines = file.readLines()
                csvData.addAll(lines.map { it.split(",") })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return csvData
    }

    private fun calculateAverage(values: List<Float>): Float {
        return if (values.isNotEmpty()) values.average().toFloat() else 0F
    }

    private fun findMaxValueAndIndex(values: List<Float>): Pair<Float, Int> {
        var maxValue = Float.MIN_VALUE
        var maxIndex = -1

        for ((index, value) in values.withIndex()) {
            if (value > maxValue) {
                maxValue = value
                maxIndex = index
            }
        }

        return Pair(maxValue, maxIndex)
    }

    private fun findSecondMaxValueAndIndex(values: List<Float>, indexToExclude: Int): Pair<Float, Int> {
        var secondMaxValue = Float.MIN_VALUE
        var secondMaxIndex = -1

        for ((index, value) in values.withIndex()) {
            if (index != indexToExclude && value > secondMaxValue) {
                secondMaxValue = value
                secondMaxIndex = index
            }
        }

        return Pair(secondMaxValue, secondMaxIndex)
    }
}


