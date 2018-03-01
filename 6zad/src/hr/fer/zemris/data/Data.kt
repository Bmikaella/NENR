package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */

class Data(val dot: Dot, val output: Double) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("x=${dot.x} y=${dot.y} f=$output")
        return builder.toString()
    }
}