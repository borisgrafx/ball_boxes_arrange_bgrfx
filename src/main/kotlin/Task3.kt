import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.BitmapEncoder.saveBitmapWithDPI
import org.knowm.xchart.QuickChart.getChart
import org.knowm.xchart.style.markers.SeriesMarkers.NONE
import java.awt.Color
import kotlin.math.abs

fun task3(iterations: Int) {
    val exp = parseData()
    val box1 = Box(13, 65, 36, 43, 60, 83)
    val box2 = Box(17, 53, 44, 32, 23, 61)
    val box3 = Box(13, 53, 14, 54, 68, 48)
    val box4 = Box(11, 82, 78, 39, 83, 7)
    val box5 = Box(77, 67, 16, 48, 18,54)
    val boxes = listOf(box1, box2, box3, box4, box5)
    val firstBox = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val secondBox = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val thirdBox = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val fourthBox = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val fifthBox = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val distributionBox = arrayListOf(firstBox, secondBox, thirdBox, fourthBox, fifthBox)
    var probDistBox: List<List<Double>> = emptyList()
    val dataForGraph = mutableListOf<MutableList<MutableList<Double>>>()
    for (i in 0..5) {
        dataForGraph.add(mutableListOf())
        for (j in 0..5) dataForGraph[i].add(mutableListOf())
    }
    for (i in 0 until iterations) {
        distributionBox[i % 5] = calcCntOfBalls(exp[i], distributionBox[i % 5])
        probDistBox = calcDistrProb(distributionBox)
        for (j in 0..5) dataForGraph[i % 5][j].add(probDistBox[i % 5][j])
    }
    val theory = calcDistrProbBox(boxes)
    println("3 Пункт")
    println("Распределение цветных шаров по коробкам из $iterations итераций:")
    println("Вероятность шаров: Red, White, Black, Green, Blue, Yellow")
    for (i in 1..5) println("$i Коробка: " + probDistBox[i - 1])
    println("Теоретическая вероятность шаров: Red, White, Black, Green, Blue, Yellow")
    for (i in 1..5) println("$i Коробка: " + theory[i - 1])
    graph3(dataForGraph, iterations)
    val perms = permutations()
    var minDelta = 5.0
    var answ = mutableListOf<Int>()
    for (perm in perms) {
        var delta = 0.0
        for (i in 0..4) for (j in 0..4) delta += abs(theory[perm[i]][j] - probDistBox[i][j])
        if (minDelta > delta) {
            minDelta = delta
            answ = perm.toMutableList()
        }
    }
    for (i in 0..4) answ[i]++
    println("Оптимальная перестановка с минимальным отклонением от теоретического - $answ")
    println("Отклонение $minDelta")
}

fun calcCntOfBalls(data: List<String>, cntOfBalls: List<Double>): List<Double> {
    val terms = arrayListOf<Double>()
    for (i in 0..5) terms.add(cntOfBalls[i])
    for (color in data)
        when (color) {
            "Red" -> terms[0]++
            "White" -> terms[1]++
            "Black" -> terms[2]++
            "Green" -> terms[3]++
            "Blue" -> terms[4]++
            "Yellow" -> terms[5]++
        }
    return terms
}

fun calcDistrProb(data: List<List<Double>>): List<List<Double>> {
    val answ = listOf<MutableList<Double>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )
    for (i in 0..4) for (j in 0..5) answ[i].add(data[i][j] / data[i].sum())
    return answ
}

fun calcDistrProbBox(data: List<Box>): List<List<Double>> {
    val answ = listOf<MutableList<Double>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )
    for (i in 0..4) {
        answ[i].add(data[i].red / data[i].total())
        answ[i].add(data[i].white / data[i].total())
        answ[i].add(data[i].black / data[i].total())
        answ[i].add(data[i].green / data[i].total())
        answ[i].add(data[i].blue / data[i].total())
        answ[i].add(data[i].yellow / data[i].total())
    }
    return answ
}

fun graph3(data: MutableList<MutableList<MutableList<Double>>>, n: Int) {
    val xData = doubleArrayOf().toMutableList()
    for (i in 1..(n.toDouble() / 5).toInt()) xData.add(i.toDouble() * 5)
    for (i in 0..4) {
        val k = i + 1
        val chart = getChart("Профиль корзины $k:", "N", "P", "Красный", xData, data[i][0])
        chart.styler.seriesColors =
            arrayOf(Color.red, Color.gray, Color.black, Color.green, Color.blue, Color.orange)
        chart.styler.seriesMarkers = arrayOf(NONE, NONE, NONE, NONE, NONE)
        chart.addSeries("Белый", xData, data[i][1])
        chart.addSeries("Чёрный", xData, data[i][2])
        chart.addSeries("Зелёный", xData, data[i][3])
        chart.addSeries("Синий", xData, data[i][4])
        chart.addSeries("Жёлтый", xData, data[i][5])
        saveBitmapWithDPI(chart, "./results/3c-$k", BitmapFormat.JPG, 400)
    }
}