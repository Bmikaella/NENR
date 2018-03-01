package hr.fer.zemris.genetic.chromosome

import hr.fer.zemris.Preconditions
import java.util.*

/**
 * Created by bmihaela.
 */
class Chromosome(size: Int) : Iterable<Double> {
    val data: DoubleArray = kotlin.DoubleArray(size)

    init {
        val random = Random()
        for (index in 0 until size) {
            data[index] = random.nextGaussian()
        }
    }

    constructor(newData: DoubleArray) : this(newData.size) {
        for (i in 0 until newData.size) {
            data[i] = newData[i]
        }
    }

    fun getElementAt(index: Int): Double {
        Preconditions.requireTo(index >= 0 && index < data.size, "This n dimensional dot only has" +
                " ${data.size} dimensions so $index element can't be accessed")
        return data[index]
    }

    fun setElementAt(index: Int, value: Double) {
        Preconditions.requireTo(index >= 0 && index < data.size, "This n dimensional dot only has" +
                " ${data.size} dimensions so $index element can't be accessed")
        data[index] = value
    }

    fun getSize(): Int {
        return data.size
    }

    override fun iterator(): Iterator<Double> {
        return object : Iterator<Double> {
            var currentIndex = 0


            override fun hasNext(): Boolean {
                if (currentIndex >= data.size) {
                    return false
                }
                return true
            }

            override fun next(): Double {
                if (!hasNext()) {
                    throw IllegalStateException()
                }
                val element = data[currentIndex]
                currentIndex++
                return element
            }
        }
    }

    fun getSolutionData(): DoubleArray {
        return data.clone()
    }

    override fun toString(): String {
        val builder = StringBuilder("(")
        for (elem in this) {
            builder.append("$elem ")
        }
        builder.append(")")
        return builder.toString()
    }
}