package hr.fer.zemris.functions

import hr.fer.zemris.Preconditions

/**
 * Created by bmihaela.
 */
abstract class ErrorFunction {

    abstract fun calculateOneExampleOutput(predicted: DoubleArray, expected: DoubleArray): Double

    companion object {

        @JvmStatic
        val MSE = object : ErrorFunction() {
            override fun calculateOneExampleOutput(predicted: DoubleArray, expected: DoubleArray): Double {
                Preconditions.requireTo(predicted.size == expected.size, "Can't be of different size.")
                var result = 0.0
                for (index in 0 until predicted.size) {
                    result += Math.pow(expected[index] - predicted[index], 2.0)
                }
                return result * 0.5
            }
        }
    }
}