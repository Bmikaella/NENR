package hr.fer.zemris.neuro

import hr.fer.zemris.Util
import hr.fer.zemris.data.Data
import hr.fer.zemris.functions.ActivationFunction
import hr.fer.zemris.functions.ErrorFunction

/**
 * Created by bmihaela.
 */
class NeuralNetwork(val layers: MutableList<Layer>, val errorFunction: ErrorFunction) {

    companion object {
        @JvmStatic
        fun createNetwork(arhitecture: IntArray, activationFunction: ActivationFunction, outputSubjectedToAF: Boolean,
                          errorFunction: ErrorFunction, weightInitialization: Int): NeuralNetwork {
            var lastLayerNumberOfNeurons = 1
            val layers = mutableListOf<Layer>()
            for (layerIndex in 0 until arhitecture.size) {
                var layer: Layer
                if (layerIndex == arhitecture.size - 1 && !outputSubjectedToAF) {
                    layer = Layer.createLayer(arhitecture[layerIndex], lastLayerNumberOfNeurons, ActivationFunction.IDENTITY, weightInitialization)
                } else {
                    layer = Layer.createLayer(arhitecture[layerIndex], lastLayerNumberOfNeurons, activationFunction, weightInitialization)
                }
                lastLayerNumberOfNeurons = arhitecture[layerIndex]
                layers.add(layer)
            }
            return NeuralNetwork(layers, errorFunction)
        }

    }

    fun train(trainData: MutableList<Data>, maxIterations: Int, batchSize: Int, learningRate: Double, status: Boolean = false,
              iterationStatus: Int = 500, learningRateDecay: Double = 1.0, learningRateDecayIteration: Int = 10000000) {
        var currentLearningRate = learningRate
        var currentIndex = 0
        var iterations = 0
        do {
            val batchData = Util.takeNNextElements(trainData, batchSize, currentIndex)
            currentIndex = batchData.second

            for (data in batchData.first) {
                this.forwardPass(data.xVector)
                this.backPropagate(data.yVector)
            }

            this.optimize(currentLearningRate)
            if (status && iterations % iterationStatus == 0) {
                println(getError(trainData))
            }
            if (iterations % learningRateDecayIteration == 0) {
                currentLearningRate *= learningRateDecay
            }
            iterations++
        } while (iterations < maxIterations)
    }

    fun getError(data: MutableList<Data>): Double {
        var error = 0.0
        for (unit in data) {
            error += errorFunction.calculateOneExampleOutput(unit.yVector, forwardPass(unit.xVector))
        }
        return error / data.size
    }

    fun predictClassification(input: DoubleArray): Int {
        val output = forwardPass(input)
        var max = output[0]
        var maxIndex = 0
        for (i in 0 until output.size) {
            if (max < output[i]) {
                max = output[i]
                maxIndex = i
            }
        }
        return maxIndex
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

    fun optimize(learningRate: Double) {
        for (layer in layers) {
            layer.optimize(learningRate)
        }
    }

    fun backPropagate(realValues: DoubleArray) {
        val output = DoubleArray(realValues.size, { 0.0 })
        val lastLayer = layers[layers.size - 1]
        for (index in 0 until lastLayer.abstractNeurons.size) {
            output[index] = lastLayer.abstractNeurons[index].output
        }
        var lastError: DoubleArray = errorFunction.calculateDerivativeOutput(realValues, output)
        for (index in layers.size - 1 downTo 0) {
            lastError = layers[index].backwardPass(lastError)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (layer in layers) {
            builder.append("$layer \n")
        }
        return builder.toString()
    }
}