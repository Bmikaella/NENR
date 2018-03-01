package hr.fer.zemris.functions

import hr.fer.zemris.Preconditions

/**
 * Created by bmihaela.
 */
abstract class ErrorFunction {

    abstract fun calculateOneExampleOutput(x: DoubleArray, y: DoubleArray): Double

    abstract fun calculateDerivativeOutput(x: DoubleArray, y: DoubleArray): DoubleArray

    companion object {

        @JvmStatic
        val MSE = object : ErrorFunction() {
            override fun calculateOneExampleOutput(x: DoubleArray, y: DoubleArray): Double {
                Preconditions.requireTo(x.size == y.size, "Can't be of different size.")
                var result = 0.0
                for (index in 0 until x.size) {
                    result += Math.pow(x[index] - y[index], 2.0)
                }
                return result * 0.5
            }

            override fun calculateDerivativeOutput(x: DoubleArray, y: DoubleArray): DoubleArray {
                Preconditions.requireTo(x.size == y.size, "Can't be of different size.")
                val newArray: DoubleArray = DoubleArray(x.size, { 0.0 })
                for (index in 0 until x.size) {
                    newArray[index] = x[index] - y[index]
                }
                return newArray
            }
        }
    }
}