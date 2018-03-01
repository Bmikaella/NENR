package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */

class Dot(val x: Double, val y: Double) {

    companion object {
        @JvmStatic
        fun euclideanDistance(first: Dot, second: Dot): Double {
            return Math.sqrt(Math.pow(first.x - second.x, 2.0) + Math.pow(first.y - second.y, 2.0))
        }
    }
}