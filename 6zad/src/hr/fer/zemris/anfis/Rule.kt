package hr.fer.zemris.anfis

/**
 * Created by bmihaela.
 */
class Rule {
    val xCompatibility = SigCompatibility()
    val yCompatibility = SigCompatibility()

    val xUndYAmor = Konzekvensus()

    fun getAntecedensWeights(x: Double, y: Double): XYWeight {
        return XYWeight(yCompatibility.compute(y), xCompatibility.compute(x))
    }

    fun getRuleWeight(xyWeight: XYWeight): Double {
        return xyWeight.wx * xyWeight.wy
    }

    fun howMuchLoveIsThere(x: Double, y: Double): Double {
        return xUndYAmor.compute(x, y)
    }

    fun addKonzekvensGradient(learningRate: Double) {
        xUndYAmor.addGradient(learningRate)
    }

    fun addCompatibilityGradient(learningRate: Double) {
        xCompatibility.addGradient(learningRate)
        yCompatibility.addGradient(learningRate)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(xCompatibility)
        builder.append(yCompatibility)
        builder.append(xUndYAmor)
        return builder.toString()
    }

    fun getAntecedensFunctionsAsText() : String{
        return xCompatibility.getSigText() + "\n" + yCompatibility.getSigText()
    }
}