package hr.fer.zemris

import hr.fer.zemris.anfis.ANFIS
import hr.fer.zemris.data.Data
import hr.fer.zemris.data.Dot
import java.util.*

/**
 * Created by bmihaela.
 */
class ANFISDemo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = mutableListOf<Data>()
            for (x in -4..4) {
                for (y in -4..4) {
                    data.add(Data(Dot(x.toDouble(), y.toDouble()), testFun(x.toDouble(), y.toDouble())))
                }
            }

            val random = Random()
            for (i in 0..80) {
                val newIndex = random.nextInt(81)
                val tempUnit = data[newIndex]
                data[newIndex] = data[i]
                data[i] = tempUnit
            }

            val anfis = ANFIS(12)
            anfis.train(data, 0.2, 9, 4_500, true, 9, 0.000008, 0.9, 8_000)

            val xData = mutableListOf<Double>()
            val yData = mutableListOf<Double>()
            val outputData = mutableListOf<Double>()
            for (unit in data) {
                xData.add(unit.dot.x)
                yData.add(unit.dot.y)
                val diff = Math.abs(anfis.forwardPass(unit.dot.x, unit.dot.y) - unit.output)
                outputData.add(diff)
                println("${diff}")
            }

            xData.forEach { print("$it,") }
            println()
            println()
            yData.forEach { print("$it,") }
            println()
            println()
            outputData.forEach { print("$it,") }

        }

        @JvmStatic
        fun testFun(x: Double, y: Double): Double {
            val result = Math.pow(x - 1, 2.0) + Math.pow(y + 2, 2.0) - 5 * x * y + 3
            return result * Math.pow(Math.cos(x / 5.0), 2.0)
        }
    }
}