package hr.fer.zemris.neuro.neurons

import hr.fer.zemris.neuro.neurons.impl.IdentityNeuron
import hr.fer.zemris.neuro.neurons.impl.Neuron1
import hr.fer.zemris.neuro.neurons.impl.SigmoidNeuron

/**
 * Created by bmihaela.
 */
enum class NeuronType(val typeName: String) {
    IDENTITY("I"),
    SIGMOIDAL("S"),
    ONE("O");

    companion object {
        fun getNeuronType(string: String): NeuronType {
            when (string) {
                IDENTITY.typeName -> return IDENTITY
                SIGMOIDAL.typeName -> return SIGMOIDAL
                ONE.typeName -> return ONE
            }
            return SIGMOIDAL
        }

        fun getNeuron(neuronType: NeuronType, numberOfInputs: Int): AbstractNeuron {
            when (neuronType) {
                IDENTITY -> return IdentityNeuron()
                SIGMOIDAL -> return SigmoidNeuron(numberOfInputs)
                ONE -> return Neuron1(numberOfInputs)
            }
        }

    }
}