package hr.fer.zemris.anfis

import java.util.*

/**
 * Created by bmihaela.
 */
class Konzekvensus {

    var p: Double = Random().nextDouble()*2 - 1
    var q: Double = Random().nextDouble()*2 - 1
    var r: Double = Random().nextDouble()*2 - 1

    var gradientP: Double = 0.0
    var gradientQ: Double = 0.0
    var gradientR: Double = 0.0

    fun compute(x: Double, y: Double): Double {
        return p * x + q * y + r
    }

    fun addGradient(learningRate: Double) {
        p += learningRate * gradientP
        q += learningRate * gradientQ
        r += learningRate * gradientR

        gradientQ = 0.0
        gradientR = 0.0
        gradientP = 0.0
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("p=$p q=$q r=$r\n")
        builder.append("gradients: p=$gradientP q=$gradientQ r=$gradientR\n")
        return builder.toString()
    }
}