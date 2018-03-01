package hr.fer.zemris.neuro

import hr.fer.zemris.neuro.neurons.AbstractNeuron
import hr.fer.zemris.neuro.neurons.NeuronType

/**
 * Created by bmihaela.
 */
class Layer(val abstractNeurons: MutableList<AbstractNeuron>) {

    companion object {

        @JvmStatic
        val LAYERS_DELLIMITER = "x"

        @JvmStatic
        fun createLayer(numberOfNeurons: Int, numberOfInputsForEachNeuron: Int, neuronType: NeuronType): Layer {
            val neurons = mutableListOf<AbstractNeuron>()
            for (i in 0 until numberOfNeurons) {
                neurons.add(NeuronType.getNeuron(neuronType, numberOfInputsForEachNeuron))
            }
            return Layer(neurons)
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


    override fun toString(): String {
        val builder = StringBuilder("")
        for (neuron in abstractNeurons) {
            builder.append("---")
            builder.append(neuron).append("\n")
        }
        return builder.toString()
    }
}