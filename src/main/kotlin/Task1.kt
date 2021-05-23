import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.BitmapEncoder.saveBitmapWithDPI
import org.knowm.xchart.QuickChart.getChart

fun task1(iterations: Int) {
    val exp = parseData()
    val box1 = Box(13, 65, 36, 43, 60, 83)
    val box2 = Box(17, 53, 44, 32, 23, 61)
    val box3 = Box(13, 53, 14, 54, 68, 48)
    val box4 = Box(11, 82, 78, 39, 83, 7)
    val box5 = Box(77, 67, 16, 48, 18,54)
    val boxes = listOf(box1, box2, box3, box4, box5)
    val firstBox = listOf(1.0, 1.0, 1.0, 1.0, 1.0)
    val secondBox = listOf(1.0, 1.0, 1.0, 1.0, 1.0)
    val thirdBox = listOf(1.0, 1.0, 1.0, 1.0, 1.0)
    val fourthBox = listOf(1.0, 1.0, 1.0, 1.0, 1.0)
    val fifthBox = listOf(1.0, 1.0, 1.0, 1.0, 1.0)
    val distributionBox = arrayListOf(firstBox, secondBox, thirdBox, fourthBox, fifthBox)
    var hypoProb: ArrayList<Double> = arrayListOf()
    for (i in 0..119) hypoProb.add(1.0 / 120)
    var sortedHypoProb = mutableMapOf<Int, Double>()
    val dataForGraph = mutableListOf<MutableList<Double>>()
    for (i in 0..10) dataForGraph.add(mutableListOf())
    var k = 0
    for (i in 0 until iterations) {
        distributionBox[i % 5] = calculateProb(boxes, exp[i], arrayListOf(), true)
        if (i % 5 == 4) {
            hypoProb = calcProbeForHypo(distributionBox, hypoProb)
            fillProbForGraph(hypoProb, dataForGraph)
            for (j in hypoProb.indices) sortedHypoProb[j] = hypoProb[j]
            k++
        }
    }
    println("1 пункт")
    println("$k итераций прохождения пяти корзин")
    println("Перестановки и их номера:")
    var count = 0
    permutations().forEach { list -> print("$count [ ")
        list.forEach { print("${it + 1} ") }
        println("]")
        count++
    }
    count = 0
    sortedHypoProb = sortedHypoProb.toList().sortedBy { (_, v) -> v }.toMap().toMutableMap()
    println("Номер гипотезы => её вероятность :")
    sortedHypoProb.forEach { (k, v) -> println("$k => $v") }
    println("Максимальная вероятность ${hypoProb.maxOrNull()}")
    graph1(dataForGraph, k)
}

fun calcProbeForHypo(data: List<List<Double>>, priorProb: List<Double>): ArrayList<Double> {
    val terms = arrayListOf<Double>()
    for (i in 0..119) terms.add(priorProb[i])
    val perm = permutations()
    for (term in 0..119) {
        var probOfHypoN = 1.0
        probOfHypoN *= data[0][perm[term][0]]
        probOfHypoN *= data[1][perm[term][1]]
        probOfHypoN *= data[2][perm[term][2]]
        probOfHypoN *= data[3][perm[term][3]]
        probOfHypoN *= data[4][perm[term][4]]
        terms[term] *= probOfHypoN
    }
    val postProb = arrayListOf<Double>()
    for (i in 0..119) postProb.add((terms[i]) / terms.sum())
    return postProb
}

fun fillProbForGraph(probs: ArrayList<Double>, data: List<MutableList<Double>>) {
    data[0].add(probs[51])
    data[1].add(probs[57])
    data[2].add(probs[9])
    data[3].add(probs[3])
    data[4].add(probs[33])
    data[5].add(probs[27])
    data[6].add(probs[50])
    data[7].add(probs[8])
    data[8].add(probs[56])
    data[9].add(probs[71])
    data[10].add(probs.filter { it > 0.1 }.size.toDouble())
}

fun graph1(data: List<MutableList<Double>>, k: Int) {
    val xData = doubleArrayOf().toMutableList()
    for (i in 1..k) xData.add(i * 5.0)
    val chart = getChart("Вероятности гипотез", "N", "P", "[3 1 4 5 2]", xData, data[0])
    chart.addSeries("[3 2 4 5 1]", xData, data[1])
    chart.addSeries("[1 3 4 5 2]", xData, data[2])
    chart.addSeries("[1 2 4 5 3]", xData, data[3])
    chart.addSeries("[2 3 4 5 1]", xData, data[4])
    chart.addSeries("[2 1 4 5 3]", xData, data[5])
    chart.addSeries("[3 1 4 2 5]", xData, data[6])
    chart.addSeries("[1 3 4 2 5]", xData, data[7])
    chart.addSeries("[3 2 4 1 5]", xData, data[8])
    chart.addSeries("[3 5 4 2 1]", xData, data[9])
    saveBitmapWithDPI(chart, "./results/1a", BitmapFormat.JPG, 400)
    val chart1 =
        getChart("Вероятность наилучшей гипотезы", "N", "P", "[3 1 4 5 2]", xData, data[0])
    saveBitmapWithDPI(chart1, "./results/1", BitmapFormat.JPG, 400)
    val chart2 =
        getChart("Количество превалирующих гипотез", "N", "P", "Количество гипотез", xData, data[10])
    saveBitmapWithDPI(chart2, "./results/1c", BitmapFormat.JPG, 400)
}