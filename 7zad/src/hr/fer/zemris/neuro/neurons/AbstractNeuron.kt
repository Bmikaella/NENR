package hr.fer.zemris.neuro.neurons

/**
 * Created by bmihaela.
 */

abstract class AbstractNeuron {

    companion object {

        @JvmStatic
        val NEURON_CONFIGURATION_DELIMITER = "-"
    }

    abstract fun forwardPass(inputs: DoubleArray): Double

    abstract fun setWeights(weights: DoubleArray, offset: Int): Int

    abstract fun getNumberOfParameters(): Int
}