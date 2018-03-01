package hr.fer.zemris.anfis

import java.util.*

/**
 * Created by bmihaela.
 */
class SigCompatibility {

    var a: Double = Random().nextDouble()*2 - 1
    var b: Double = Random().nextDouble()*2 - 1

    var gradientA: Double = 0.0
    var gradientB: Double = 0.0

    fun compute(input: Double): Double {
        return 1.0 / (1 + Math.exp(b * (input - a)))
    }

    fun addGradient(learningRate: Double) {
        a += learningRate * gradientA
        b += learningRate * gradientB
        gradientA = 0.0
        gradientB = 0.0
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("a=$a b=$b\n")
        builder.append("gradient: a=$gradientA b=$gradientB\n")
        return builder.toString()
    }

    fun getSigText() : String{
        return "1/(1+ exp($b*(x-$a)))"
    }
}