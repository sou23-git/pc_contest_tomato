package app.pc_contest.tomato


fun main() {

    val maxRows = 3

    val tempAddList = listOf("-", "-")

    val tempList = mutableListOf(
        listOf("0", "A"),
        listOf("1", "B"),
        listOf("2", "C"))

    var fileTemp = tempList

    for(i in 0..1){
        if(fileTemp.size <= maxRows){
            fileTemp.add(tempAddList)
            print("writed")
        } else {
            fileTemp.removeAt(0)
            fileTemp.add(tempAddList)
            print("rewrited")
        }
        print(fileTemp)
    }

}