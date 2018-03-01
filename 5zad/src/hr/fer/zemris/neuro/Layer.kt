package hr.fer.zemris.neuro

import hr.fer.zemris.functions.ActivationFunction

/**
 * Created by bmihaela.
 */
class Layer(val abstractNeurons: MutableList<AbstractNeuron>) {


    companion object {

        @JvmStatic
        fun createLayer(numberOfNeurons: Int, numberOfInputsForEachNeuron: Int,
                        activationActivationFunction: ActivationFunction, weightInitialization: Int): Layer {
            val neurons = mutableListOf<AbstractNeuron>()
            for (i in 0 until numberOfNeurons) {
                neurons.add(Neuron.createNeuron(numberOfInputsForEachNeuron,
                        activationFunction = activationActivationFunction, weightInitialization = weightInitialization))
            }
            return Layer(neurons)
        }
    }

    fun optimize(learningRate: Double) {
        for (neuron in abstractNeurons) {
            neuron.optimize(learningRate)
        }
    }

    fun forwardPass(xVector: DoubleArray): DoubleArray {
        val neuronsOutput: DoubleArray = kotlin.DoubleArray(abstractNeurons.size, { 0.0 })
        for (index in 0 until abstractNeurons.size) {
            neuronsOutput[index] = abstractNeurons[index].forwardPass(xVector)
        }
        return neuronsOutput
    }

    //param for first layer == one input for each neuron
    fun firstLayerForwardPass(xVector: DoubleArray): DoubleArray {
        val neuronsOutput: DoubleArray = kotlin.DoubleArray(abstractNeurons.size, { 0.0 })
        for (index in 0 until abstractNeurons.size) {
            neuronsOutput[index] = abstractNeurons[index].forwardPass(doubleArrayOf(xVector[index]))
        }
        return neuronsOutput
    }

    fun backwardPass(lastError: DoubleArray): DoubleArray {
        val neuronsGradient = DoubleArray(abstractNeurons.size, { 0.0 })
        val nextBackNumberOfNeurons = abstractNeurons[0].currentInputs.size
        for (index in 0 until abstractNeurons.size) {
            neuronsGradient[index] = abstractNeurons[index].backwardPass(lastError[index])
        }
        val nextNeuronsGradients = DoubleArray(nextBackNumberOfNeurons)
        for (i in 0 until nextBackNumberOfNeurons) {
            var gradient = 0.0
            for (j in 0 until neuronsGradient.size) {
                gradient += neuronsGradient[j] * abstractNeurons[j].weights[i]
            }
            nextNeuronsGradients[i] = gradient
        }
        return nextNeuronsGradients
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (neuron in abstractNeurons) {
            builder.append(neuron).append("  ")
        }
        return builder.toString()
    }
}