import java.io.File

fun main() {
    task1(500)
    task2(1700)
    task3(10000)
}

class Box(var red: Int, var white: Int, var black: Int, var green: Int, var blue: Int, var yellow: Int) {
    fun total(): Double {
        return (red + white + black + green + blue + yellow).toDouble()
    }
}

fun parseData(): List<List<String>> {
    val file = File("src/main/resources/task_1_ball_boxes_arrange.txt")
    var count = 0
    val list = mutableListOf<List<String>>()
    for (line in file.readLines())
        if (line.contains('#')) {
            count++
            val tmp = line.replace(Regex("# [0-9]+, Balls: "), "").split(", ")
            list.add(tmp)
        }
    return list
}


fun permutations(): List<List<Int>> {
    val answer = mutableListOf<List<Int>>()
    for (a1 in 0..4)
        for (a2 in 0..4)
            for (a3 in 0..4)
                for (a4 in 0..4)
                    for (a5 in 0..4)
                        if (
                            a1 != a2 && a1 != a3 && a1 != a4 && a1 != a5 &&
                            a2 != a3 && a2 != a4 && a2 != a5 &&
                            a3 != a4 && a3 != a5 && a4 != a5
                        ) answer.add(listOf(a1, a2, a3, a4, a5))
    return answer
}

/*
fun calculateProbability( boxes: List<Box>, data: List<String>, priorProb: List<Double>):  ArrayList<Double>{


}*/
