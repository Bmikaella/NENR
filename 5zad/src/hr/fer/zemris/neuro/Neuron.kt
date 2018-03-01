package hr.fer.zemris.neuro

import hr.fer.zemris.Preconditions
import hr.fer.zemris.functions.ActivationFunction
import java.util.*

/**
 * Created by bmihaela.
 */

class Neuron(numberOfInputs: Int, override var bias: Double, override val activationFunction: ActivationFunction)
    : AbstractNeuron() {

    override val weights: DoubleArray = kotlin.DoubleArray(numberOfInputs, { 0.0 })
    override val currentInputs: DoubleArray = kotlin.DoubleArray(weights.size, { 0.0 })
    override var output: Double = 0.0
    override var localGradients: Double = 0.0

    override fun forwardPass(inputs: DoubleArray): Double {
        Preconditions.requireNeuronTo(inputs.size == weights.size, "Each input must have one and only one weight. WS: ${weights.size}, IS : ${inputs.size} ")
        output = 0.0
        for (index in 0 until weights.size) {
            currentInputs[index] = inputs[index]
            output += weights[index] * inputs[index]
        }
        output += bias
        output = activationFunction.getResult(output)
        return output
    }

    override fun optimize(learningRate: Double) {
        for (i in 0 until weights.size) {
            weights[i] += learningRate * localGradients * currentInputs[i]
        }
        bias += localGradients * learningRate
        output = 0.0
        localGradients = 0.0
    }

    override fun backwardPass(gradient: Double): Double {
        val gradientSum = gradient
        val derivativeOutput = activationFunction.getDerivative(output)
        val localGradient = gradientSum * derivativeOutput
        localGradients += localGradient
        return localGradient
    }

    override fun initializeWeights(weightInitialization: Int) {
        val random = Random()
        val multiplier = Math.sqrt(2.0 / weights.size)
        for (i in 0 until weights.size) {
            var result: Double
            if (weightInitialization == 1) {
                result = random.nextInt(weights.size) * multiplier + random.nextDouble() + random.nextGaussian()
            } else if (weightInitialization == 0) {
                result = random.nextDouble() * (random.nextInt(2) - 1)
            } else {
                result = random.nextDouble()
            }
            weights[i] = result
        }
    }

    companion object {
        @JvmStatic
        fun createNeuron(numberOfInputs: Int, activationFunction: ActivationFunction, weightInitialization: Int): Neuron {
            val neuron = Neuron(numberOfInputs, 0.0, activationFunction)
            neuron.initializeWeights(weightInitialization)
            return neuron
        }
    }
}