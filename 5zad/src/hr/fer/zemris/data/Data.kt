package hr.fer.zemris.data

/**
 * Created by bmihaela.
 */
class Data(val xVector: DoubleArray, val yVector: DoubleArray) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("")
        for (x in xVector) {
            builder.append("$x ")
        }
        builder.append("|")
        for (y in yVector) {
            builder.append("$y ")
        }
        return builder.toString()
    }
}