package app.pc_contest.tomato

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.regex.Pattern


object test {
    @JvmStatic
    fun main(args: Array<String>) {
        process("20180712.csv", ":00,", "output.csv")
    }

    fun process(read_file: String?, searchString: String?, output_file: String?) {
        try {
            FileReader(read_file).use { fr ->
                BufferedReader(fr).use { br ->
                    FileWriter(output_file).use { fw ->
                        BufferedWriter(fw).use { bw ->
                            var line: String
                            while (br.readLine().also { line = it } != null) {
                                val p =
                                    Pattern.compile(searchString)
                                val m = p.matcher(line)
                                if (m.find()) {
                                    var csvArray: Array<String>
                                    csvArray =
                                        line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                                            .toTypedArray()
                                    val time = csvArray[0]
                                    val press = csvArray[1].toFloat() % 10000
                                    val pressure =
                                        String.format("%.2f", press)
                                    val temperature = csvArray[2]
                                    val outputLine =
                                        java.lang.String.join(",", time, pressure, temperature)
                                    bw.write(outputLine)
                                    bw.newLine()
                                } else {
                                }
                            }
                        }
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}
