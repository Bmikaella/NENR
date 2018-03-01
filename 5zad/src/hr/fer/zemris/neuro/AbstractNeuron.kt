package hr.fer.zemris.neuro

import hr.fer.zemris.functions.ActivationFunction

/**
 * Created by bmihaela.
 */

abstract class AbstractNeuron {
    abstract val currentInputs: DoubleArray
    abstract val weights: DoubleArray
    abstract var output: Double
    abstract val localGradients: Double
    abstract var bias: Double

    abstract val activationFunction: ActivationFunction
    abstract fun forwardPass(inputs: DoubleArray): Double
    abstract fun backwardPass(gradient: Double): Double
    abstract fun optimize(learningRate: Double)

    override fun toString(): String {
        val builder = StringBuilder("|W: ")
        for (w in weights) {
            builder.append("$w ")
        }
        builder.append("bias: $bias")
        builder.append(" - G: ")
        builder.append("$localGradients ")
        builder.append("|")
        return builder.toString()
    }

    abstract fun initializeWeights(weightInitialization: Int)
}