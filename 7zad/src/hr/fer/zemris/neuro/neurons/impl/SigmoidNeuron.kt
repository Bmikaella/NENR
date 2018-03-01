package hr.fer.zemris.neuro.neurons.impl

import hr.fer.zemris.Preconditions
import hr.fer.zemris.functions.ActivationFunction
import hr.fer.zemris.neuro.neurons.AbstractNeuron

/**
 * Created by bmihaela.
 */
class SigmoidNeuron(numberOfInputs: Int) : AbstractNeuron() {
    val activationFunction = ActivationFunction.SIGMOID
    val weights: DoubleArray = DoubleArray(numberOfInputs)


    override fun getNumberOfParameters(): Int {
        return weights.size
    }

    override fun setWeights(weights: DoubleArray, offset: Int): Int {
        var newOffset = offset
        for (i in 0 until this.weights.size) {
            this.weights[i] = weights[newOffset]
            newOffset++
        }
        return newOffset
    }

    override fun toString(): String {
        val builder = StringBuilder("|W: ")
        for (w in weights) {
            builder.append("$w ")
        }
        return builder.toString()
    }

    override fun forwardPass(inputs: DoubleArray): Double {
        Preconditions.requireNeuronTo(inputs.size == weights.size, "Each input must have one and only one weight. WS: ${weights.size}, IS : ${inputs.size} ")
        var output = 0.0
        for (index in 0 until weights.size) {
            output += weights[index] * inputs[index]
        }
        output = activationFunction.getResult(output)
        return output
    }
}