package hr.fer.zemris.data

import hr.fer.zemris.contract.Preconditions
import java.util.*

/**
 * Created by bmihaela.
 */
//n dimensional dot ...
class Dot : Iterable<Double> {

    private val data: DoubleArray

    constructor(size: Int) {
        Preconditions.requireTo(size > 0, "Dot can't have less then zero dimensions.")
        data = DoubleArray(size)
    }

    constructor(array: DoubleArray) {
        data = array.copyOf()
    }

    constructor(value: Double) {
        data = doubleArrayOf(value)
    }

    fun getData(): DoubleArray {
        return data.clone()
    }

    fun minusValue(value: Double): Dot {
        return operatorValue(value, { elem, value -> elem - value })
    }

    fun plusValue(value: Double): Dot {
        return operatorValue(value, { elem, value -> elem + value })
    }

    fun multiplyValue(value: Double): Dot {
        return operatorValue(value, { elem, value -> elem * value })
    }

    private fun operatorValue(value: Double, operation: (Double, Double) -> Double): Dot {
        val valueMinus = Dot(this.getSize())
        this.forEachIndexed { index, d -> valueMinus.setElementAt(index, operation(d, value)) }
        return valueMinus
    }


    fun minusDot(dot: Dot): Dot {
        return operatorDot(dot, { first, second -> first - second })
    }

    fun plusDot(dot: Dot): Dot {
        return operatorDot(dot, { first, second -> first + second })
    }

    fun multiplyDot(dot: Dot): Dot {
        return operatorDot(dot, { first, second -> first * second })
    }

    private fun operatorDot(dot: Dot, operation: (Double, Double) -> Double): Dot {
        Preconditions.requireTo(this.getSize() == dot.getSize(), "Can't do operations on different dimensional dots")
        val newDot = Dot(dot.getSize())
        this.zip(dot).forEachIndexed { index, pair -> newDot.setElementAt(index, operation(pair.first, pair.second)) }
        return newDot
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Dot

        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(data)
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

    override fun toString(): String {
        val builder = StringBuilder("(")
        for (elem in this) {
            builder.append("$elem ")
        }
        builder.append(")")
        return builder.toString()
    }


    fun divideValue(value: Double): Dot {
        return operatorValue(value, { elem, value -> elem / value })
    }

    companion object {

        @JvmStatic
        fun randomDot(lowerElementLimit: Double, upperElementLimit: Double, solutionSize: Int): Dot {
            val newDot = Dot(solutionSize)
            val random = Random()
            for (i in 0 until solutionSize) {
                newDot.setElementAt(i, lowerElementLimit + random.nextDouble() * (upperElementLimit - lowerElementLimit))
            }
            return newDot
        }
    }

}