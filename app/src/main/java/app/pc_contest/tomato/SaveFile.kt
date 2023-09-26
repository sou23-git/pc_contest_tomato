package app.pc_contest.tomato

import android.content.Context
import android.os.Environment
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import java.io.File
import java.util.ArrayDeque

class SaveFile(private val context: Context) {

    fun saveCsv(csvText: List<String>) {

        val getSensorValue = GetSensorValue()
        val maxDataRows = 200
        val header = listOf("num", "time", "x_acc", "y_acc", "z_acc", "x_gyr", "y_gyr", "z_gyr")
        val fileName = "log.csv"
        val csvFilePath: String = context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString()
            ?.plus("/").plus(fileName) ?: return // 内部ストレージのDocumentのURL

        val tempList = arrayListOf(
            listOf("0", "2023", "0", "0", "0", "0", "0", "0"),
            listOf("1", "2023", "1", "1", "1", "1", "1", "1"),
            listOf("2", "2023", "2", "2", "2", "2", "2", "2"))
        var fileTemp = java.util.ArrayDeque(tempList)

        if(fileTemp.size <= 3) {
            fileTemp.add(tempList[0])
        } else {

        }

/*        // ファイルが存在しない場合のみヘッダーを書き込む
        if (!File(csvFilePath).exists()) {
            CsvWriter().open(csvFilePath, append = false) {
                writeRow(header)
            }
        }

        // ファイルを読み込んでデータを保持
        val data = if (File(csvFilePath).exists()) {
            File(csvFilePath).readLines().toMutableList()
        } else {
            mutableListOf()
        }

        // データが指定の行数を超える場合、古いデータを削除
        if (data.size >= maxDataRows) {
            Collections.rotate(data, -1)

        }
*/

    }
}
/*// 新しいデータを追加
val indexCsv = (data.firstOrNull()?.split(",")?.get(0)?.toIntOrNull() ?: 0) + 1
val newData = listOf(indexCsv.toString()) + csvText.subList(1, csvText.size) // ヘッダー以外のデータを追加
data.add(newData.joinToString(","))

// データをCSVファイルに書き込む
CsvWriter().open(csvFilePath, append = false) {
    for (line in data) {
        writeRow(line.split(","))
    }
}*/