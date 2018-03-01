package hr.fer.zemris.neuro.neurons.impl

import hr.fer.zemris.neuro.neurons.AbstractNeuron

/**
 * Created by bmihaela.
 */
class IdentityNeuron : AbstractNeuron() {
    override fun getNumberOfParameters(): Int {
        return 0
    }

    override fun setWeights(weights: DoubleArray, offset: Int): Int {
        return offset
    }

    override fun forwardPass(inputs: DoubleArray): Double {
        return inputs.component1()
    }

    override fun toString(): String {
        return "I"
    }
}