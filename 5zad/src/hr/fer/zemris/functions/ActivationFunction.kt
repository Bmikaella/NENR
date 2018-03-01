package hr.fer.zemris.functions

/**
 * Created by bmihaela.
 */
abstract class ActivationFunction {

    abstract fun getResult(x: Double): Double

    abstract fun getDerivative(x: Double): Double

    companion object {

        @JvmStatic
        val SIGMOID = object : ActivationFunction() {
            override fun getResult(x: Double): Double {
                return 1 / (1 + Math.exp(-x))
            }

            override fun getDerivative(x: Double): Double {
                return x * (1 - x)
            }
        }

        @JvmStatic
        val IDENTITY = object : ActivationFunction() {
            override fun getResult(x: Double): Double {
                return x
            }

            override fun getDerivative(x: Double): Double {
                return 1.0
            }
        }
    }

}