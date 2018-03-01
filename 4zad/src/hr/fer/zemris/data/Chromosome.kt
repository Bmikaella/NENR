package hr.fer.zemris.data

import hr.fer.zemris.contract.Preconditions

/**
 * Created by bmihaela.
 */
abstract class Chromosome : Iterable<Double> {
    abstract val data: DoubleArray

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

    abstract fun getTemplate(): IChromosomeTemplate

    abstract fun inverse(index: Int)

    abstract fun getSolutionData(): Dot

    override fun toString(): String {
        val builder = StringBuilder("(")
        for (elem in this) {
            builder.append("$elem ")
        }
        builder.append(")")
        return builder.toString()
    }
}