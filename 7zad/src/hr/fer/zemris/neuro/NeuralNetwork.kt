package hr.fer.zemris.neuro

import hr.fer.zemris.data.NNData
import hr.fer.zemris.functions.ErrorFunction
import hr.fer.zemris.neuro.neurons.AbstractNeuron
import hr.fer.zemris.neuro.neurons.NeuronType

/**
 * Created by bmihaela.
 */
class NeuralNetwork(val layers: MutableList<Layer>, val errorFunction: ErrorFunction) {

    companion object {
        @JvmStatic
        fun createNetwork(arhitecture: String, errorFunction: ErrorFunction): NeuralNetwork {
            var lastLayerNumberOfNeurons = 1
            val layers = mutableListOf<Layer>()
            val layersConfigurations = arhitecture.split(Layer.LAYERS_DELLIMITER)
            var layer: Layer
            for (layerConfigurations in layersConfigurations) {
                val neuronsCount = layerConfigurations.split(AbstractNeuron.NEURON_CONFIGURATION_DELIMITER)[0].toInt()
                val neuronType = NeuronType.getNeuronType(layerConfigurations.split("-")[1].trim())
                layer = Layer.createLayer(neuronsCount, lastLayerNumberOfNeurons, neuronType)
                lastLayerNumberOfNeurons = neuronsCount
                layers.add(layer)
            }
            return NeuralNetwork(layers, errorFunction)
        }
    }

    fun numberOfParameters(): Int {
        var sum = 0
        for (layer in layers) {
            for (neuron in layer.abstractNeurons) {
                sum += neuron.getNumberOfParameters()
            }
        }
        return sum
    }

    fun setWeights(weights: DoubleArray) {
        var lastIndex = 0
        for (layer in layers) {
            for (neuron in layer.abstractNeurons) {
                lastIndex = neuron.setWeights(weights, lastIndex)
            }
        }
    }

    fun getError(data: MutableList<NNData>): Double {
        var error = 0.0
        for (unit in data) {
            error += errorFunction.calculateOneExampleOutput(forwardPass(unit.xInput), unit.expectedOutput)
        }
        return error / data.size
    }

    fun printPredictionError(data: MutableList<NNData>) {
        var correctPrediction = 0
        for (unit in data) {
            val predict = forwardPass(unit.xInput).map { Math.round(it) }
            print("P:")
            predict.forEach(::print)
            print(" E:")
            val expectedOutput = unit.expectedOutput.map { Math.round(it) }
            expectedOutput.forEach(::print)
            if (predict == expectedOutput) {
                correctPrediction++
            }
            println()
        }
        println("Correctly predicted: $correctPrediction/${data.size}")
    }

    fun forwardPass(input: DoubleArray): DoubleArray {
        var currentOutput: DoubleArray = input
        for (index in 0 until layers.size) {
            if (index == 0) {
                currentOutput = layers[index].firstLayerForwardPass(currentOutput)
            } else {
                currentOutput = layers[index].forwardPass(currentOutput)
            }
        }
        return currentOutput
    }

    override fun toString(): String {
        val builder = StringBuilder()
        var index = 0
        for (layer in layers) {
            builder.append("$index:= \n$layer \n\n")
            index++
        }
        return builder.toString()
    }
}