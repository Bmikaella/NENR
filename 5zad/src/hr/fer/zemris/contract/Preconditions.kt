package hr.fer.zemris

import hr.fer.zemris.exception.NeuronException

/**
 * Created by bmihaela.
 */

class Preconditions {

    companion object {
        @JvmStatic
        fun requireNeuronTo(condition: Boolean, message: String) {
            if (!condition) {
                throw NeuronException(message)
            }
        }

        @JvmStatic
        fun requireTo(condition: Boolean, message: String) {
            if (!condition) {
                throw IllegalArgumentException(message)
            }
        }
    }
}