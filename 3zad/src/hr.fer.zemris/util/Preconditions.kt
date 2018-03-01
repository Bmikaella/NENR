package hr.fer.zemris.util

/**
 * Created by bmihaela.
 */


class Preconditions {

    companion object {
        @JvmStatic
        fun requireRelationTo(condition: Boolean, message: String, vararg x: String) {
            if (!condition) {
                throw IllegalArgumentException(message.format(x))
            }
        }

        @JvmStatic
        fun requireDomainTo(condition: Boolean, message: String, vararg x: String) {
            if (!condition) {
                throw IllegalArgumentException(message.format(x))
            }
        }
    }
}