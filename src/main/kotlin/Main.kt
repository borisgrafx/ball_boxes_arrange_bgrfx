import java.io.File

fun main() {
    task1(500)
    task2(1700)
    task3(10000)
}

class Box(var red: Int, var white: Int, var black: Int, var green: Int, var blue: Int, var yellow: Int) {
    fun total() = (red + white + black + green + blue + yellow).toDouble()
}

fun parseData(): List<List<String>> {
    val file = File("src/main/resources/task_1_ball_boxes_arrange.txt")
    val list = mutableListOf<List<String>>()
    for (line in file.readLines())
        if (line.contains('#')) {
            list.add(line.replace(Regex("# [0-9]+, Balls: "), "").split(", "))
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

fun calculateProb( boxes: List<Box>, data: List<String>, priorProb: List<Double>, t1: Boolean):  ArrayList<Double>{
    val terms: ArrayList<Double>
    if (t1) {
        terms = arrayListOf(1.0, 1.0, 1.0, 1.0, 1.0)
    } else {
        terms = arrayListOf()
        for (i in 0..4) {
            terms.add(priorProb[i])
        }
    }
    for (term in 0..4) {
        var probOfBoxN = 1.0
        for (color in data)
            when (color) {
                "Red" -> {
                    probOfBoxN *= boxes[term].red / boxes[term].total()
                    boxes[term].red--

                }
                "White" -> {
                    probOfBoxN *= boxes[term].white / boxes[term].total()
                    boxes[term].white--
                }
                "Black" -> {
                    probOfBoxN *= boxes[term].black / boxes[term].total()
                    boxes[term].black--
                }
                "Green" -> {
                    probOfBoxN *= boxes[term].green / boxes[term].total()
                    boxes[term].green--
                }
                "Blue" -> {
                    probOfBoxN *= boxes[term].blue / boxes[term].total()
                    boxes[term].blue--
                }
                "Yellow" -> {
                    probOfBoxN *= boxes[term].yellow / boxes[term].total()
                    boxes[term].yellow--
                }
            }
        terms[term] *= probOfBoxN
        for (color in data)
            when (color) {
                "Red" -> boxes[term].red++
                "White" -> boxes[term].white++
                "Black" -> boxes[term].black++
                "Green" -> boxes[term].green++
                "Blue" -> boxes[term].blue++
                "Yellow" -> boxes[term].yellow++
            }
    }
    return if(t1) {
        terms
    }
    else {
        val newPostProb = arrayListOf<Double>()
        for (i in 0..4) newPostProb.add(terms[i] / (terms[0] + terms[1] + terms[2] + terms[3] + terms[4]))
        newPostProb
    }
}

