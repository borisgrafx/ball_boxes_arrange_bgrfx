import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.BitmapEncoder.saveBitmapWithDPI
import org.knowm.xchart.QuickChart.getChart

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
        distributionBox[i % 5] = calculateProb(boxes, exp[i], distributionBox[i % 5], false)
        for (j in 0..4) dataForGraph[i % 5][j].add(distributionBox[i % 5][j])
    }
    println("2 Пункт")
    println("Вероятность быть 1 коробкой, 2 коробкой, 3 коробкой, 4 коробкой, 5 коробкой")
    for (i in 1..5) println("$i Kopoбка: " + distributionBox[i - 1])
    graph2(dataForGraph, iterations)
}

fun graph2(data: MutableList<MutableList<MutableList<Double>>>, n: Int) {
    val xData = doubleArrayOf().toMutableList()
    for (i in 1..(n.toDouble() / 5).toInt()) xData.add(i.toDouble() * 5)
    for (i in 0..4){
        val k = i + 1
        val chart = getChart("Вероятность корзины $k:", "N", "P", "Первая", xData, data[i][0])
        chart.addSeries("Вторая", xData, data[i][1])
        chart.addSeries("Третья", xData, data[i][2])
        chart.addSeries("Четвёртая", xData, data[i][3])
        chart.addSeries("Пятая", xData, data[i][4])
        saveBitmapWithDPI(chart, "./results/2-$k", BitmapFormat.JPG, 400)
    }
}