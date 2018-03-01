package hr.fer.zemris.neuro.neurons.impl

import hr.fer.zemris.Preconditions
import hr.fer.zemris.neuro.neurons.AbstractNeuron

/**
 * Created by bmihaela.
 */
class Neuron1(numberOfInputs: Int) : AbstractNeuron() {
    val weights = DoubleArray(numberOfInputs)
    val sWeights = DoubleArray(numberOfInputs)

    override fun getNumberOfParameters(): Int {
        return 2 * weights.size
    }

    override fun setWeights(weights: DoubleArray, offset: Int): Int {
        var newOffset = offset
        for (i in 0 until this.weights.size) {
            this.weights[i] = weights[newOffset]
            this.sWeights[i] = weights[++newOffset]
            newOffset++
        }
        return newOffset
    }

    override fun forwardPass(inputs: DoubleArray): Double {
        Preconditions.requireNeuronTo(inputs.size == weights.size, "Each input must have one and only one weight. WS: ${weights.size}, IS : ${inputs.size} ")
        var output = 0.0
        for (index in 0 until weights.size) {
            output += Math.abs((inputs[index] - weights[index]) / sWeights[index])
        }
        output = 1 / (1 + output)
        return output
    }

    override fun toString(): String {
        val builder = StringBuilder("|w,sw||")
        for(index in 0 until weights.size){
            builder.append("${weights[index]}, ${sWeights[index]} | ")
        }
        return builder.toString()
    }
}