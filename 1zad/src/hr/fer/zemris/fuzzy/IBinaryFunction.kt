package hr.fer.zemris.fuzzy

/**
 * Created by bmihaela.
 */
interface IBinaryFunction {
    fun valueAt(first: Double, second: Double): Double
}