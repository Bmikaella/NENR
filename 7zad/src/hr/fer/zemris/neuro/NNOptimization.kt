package hr.fer.zemris.neuro

import hr.fer.zemris.data.NNData
import hr.fer.zemris.functions.OptimizingFunction

/**
 * Created by bmihaela.
 */
class NNOptimization(val neuralNetwork: NeuralNetwork, val data: MutableList<NNData>) : OptimizingFunction() {

    override fun calculate(input: DoubleArray): Double {
        neuralNetwork.setWeights(input)
        return neuralNetwork.getError(data)
    }

}