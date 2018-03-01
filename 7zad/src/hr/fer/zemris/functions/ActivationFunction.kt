package hr.fer.zemris.functions

/**
 * Created by bmihaela.
 */
abstract class ActivationFunction {

    abstract fun getResult(x: Double): Double

    companion object {

        @JvmStatic
        val SIGMOID = object : ActivationFunction() {
            override fun getResult(x: Double): Double {
                return 1 / (1 + Math.exp(-x))
            }
        }
    }
}