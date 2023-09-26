package app.pc_contest.tomato

import android.content.Context
import android.os.Environment
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter

class SaveFile(context: Context) {

    private val maxDataRows = 200
    private val header = listOf("time", "x_acc", "y_acc", "z_acc", "x_gyr", "y_gyr", "z_gyr")
    private val fileName = "log.csv"
    private val csvFilePath: String = context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString()
                ?.plus("/").plus(fileName)  // 内部ストレージのDocumentのURL

    private val listText = mutableListOf(header)


    fun writeList(csvText: List<String>) {

        //maxDataRaws以下ならそのまま書き込み、それ以上なら古いデータ削除&末尾に書き込み
        if (listText.size <= maxDataRows) {
            listText.add(csvText)
        } else {
            listText.removeAt(1)
            listText.add(csvText)
        }

    }

    fun writeCsv() {
        csvWriter().writeAll(listText, csvFilePath)
    }
}