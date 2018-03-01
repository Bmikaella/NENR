package hr.fer.zemris

/**
 * Created by bmihaela.
 */

class Preconditions {

    companion object {

        @JvmStatic
        fun requireTo(condition: Boolean, message: String) {
            if (!condition) {
                throw IllegalArgumentException(message)
            }
        }
    }
}