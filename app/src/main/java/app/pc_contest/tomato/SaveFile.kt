package app.pc_contest.tomato

import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {
    val csvFilePath = "data.csv"
    val header = listOf("num", "time", "x", "y", "z")
    val maxDataRows = 100

    while (true) {
        // 新しいデータを生成
        val newData = generateData()

        // ファイルが存在しない場合はヘッダー行を書き込む
        val file = File(csvFilePath)
        if (!file.exists()) {
            file.writeText(header.joinToString(","))
        }

        // ファイルを読み込んでデータを保持
        val data = file.readLines().toMutableList()

        // データが100行を超える場合、古いデータを削除
        if (data.size >= maxDataRows) {
            data.removeAt(0)
        }

        // 新しいデータを追加
        data.add(newData.joinToString(","))

        // データをCSVファイルに書き込む
        CsvWriter().open(csvFilePath, append = false) {
            writeRow(header)
            for (line in data) {
                writeRow(line.split(","))
            }
        }

        println("新しいデータをCSVファイルに書き込みました。")

        // インターバルを設定（例: 10秒ごとに新しいデータを生成）
        Thread.sleep(10000)
    }
}

fun generateData(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val currentTime = LocalDateTime.now().format(formatter)
    val random = Random()
    val x = random.nextDouble().toString()
    val y = random.nextDouble().toString()
    val z = random.nextDouble().toString()
    return listOf(currentTime, x, y, z)
}
