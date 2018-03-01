package hr.fer.zemris.contract

import hr.fer.zemris.exception.FunctionException

/**
 * Created by bmihaela.
 */
class Preconditions {

    companion object {
        @JvmStatic
        fun requireFunctionTo(condition: Boolean, message: String, vararg x: kotlin.Any) {
            if (!condition) {
                throw FunctionException(message.format(x))
            }
        }

        @JvmStatic
        fun requireTo(condition: Boolean, message: String, vararg x: kotlin.Any) {
            if (!condition) {
                throw IllegalArgumentException(message.format())
            }
        }
    }
}