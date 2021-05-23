import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.QuickChart

fun task2(iterations: Int) {
    val exp = parseData()
    val box1 = Box(13, 65, 36, 43, 60, 83)
    val box2 = Box(17, 53, 44, 32, 23, 61)
    val box3 = Box(13, 53, 14, 54, 68, 48)
    val box4 = Box(11, 82, 78, 39, 83, 7)
    val box5 = Box(77, 67, 16, 48, 18,54)
    val boxes = listOf(box1, box2, box3, box4, box5)
    val firstBox = listOf(1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5)
    val secondBox = listOf(1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5)
    val thirdBox = listOf(1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5)
    val fourthBox = listOf(1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5)
    val fifthBox = listOf(1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5, 1.0 / 5)
    val distributionBox = arrayListOf(firstBox, secondBox, thirdBox, fourthBox, fifthBox)
    val dataForGraph = mutableListOf<MutableList<MutableList<Double>>>()
    for (i in 0..4) {
        dataForGraph.add(mutableListOf())
        for (j in 0..4) dataForGraph[i].add(mutableListOf())
    }
    for (i in 0 until iterations) {
        distributionBox[i % 5] = calcProbPunkt2(boxes, exp[i], distributionBox[i % 5])
        for (j in 0..4) {
            dataForGraph[i % 5][j].add(distributionBox[i % 5][j])
        }
    }
    println("2 Пункт")
    println("Вероятность быть 1 коробкой,       2 коробкой,         3 коробкой,         4 коробкой,         5 коробкой")
    for (i in 1..5) {
        println("$i Kopoбка: " + distributionBox[i - 1])
    }
    graphP2(dataForGraph, iterations)
}

fun calcProbPunkt2(boxes: List<Box>, data: List<String>, priorProb: List<Double>): List<Double> {
    val terms = arrayListOf<Double>()
    for (i in 0..4) {
        terms.add(priorProb[i])
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
    val prob = terms[0] + terms[1] + terms[2] + terms[3] + terms[4]
    val newPostProb = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)
    for (i in 0..4) {
        newPostProb[i] = (terms[i]) / prob
    }
    return newPostProb
}

fun graphP2(data: MutableList<MutableList<MutableList<Double>>>, n: Int) {
    val xData = doubleArrayOf().toMutableList()
    for (i in 1..(n.toDouble() / 5).toInt()) {
        xData.add(i.toDouble() * 5)
    }
    for (i in 0..4){
        val k = i + 1
        val chart = QuickChart.getChart("Вероятность корзины $k:", "N", "P", "Первая", xData, data[i][0])
        chart.addSeries("Вторая", xData, data[i][1])
        chart.addSeries("Третья", xData, data[i][2])
        chart.addSeries("Четвёртая", xData, data[i][3])
        chart.addSeries("Пятая", xData, data[i][4])
        BitmapEncoder.saveBitmapWithDPI(chart, "./results/punkt_2_urn-$k", BitmapEncoder.BitmapFormat.JPG, 400)
    }
}