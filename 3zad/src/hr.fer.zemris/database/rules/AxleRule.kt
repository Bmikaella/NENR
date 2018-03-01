package hr.fer.zemris.database.rules

import hr.fer.zemris.boat.BoatSets
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.Operations
import hr.fer.zemris.fuzzy.impl.CalculatedFuzzySet

/**
 * Created by bmihaela.
 */

abstract class AxleRule {
    abstract val rightShore: CalculatedFuzzySet
    abstract val leftShore: CalculatedFuzzySet
    abstract val inputSpeed: CalculatedFuzzySet
    abstract val outputAxel: CalculatedFuzzySet

    fun getFuzzySetConclusion(rightDistance: Int, leftDistance: Int, speed: Int): IFuzzySet {
        val rightShoreMi = rightShore.getValueAt(DomainElement(intArrayOf(rightDistance)))
        val leftShoreMi = leftShore.getValueAt(DomainElement(intArrayOf(leftDistance)))
        val inputSpeedMi = leftShore.getValueAt(DomainElement(intArrayOf(speed)))

        val minMi = minOf(maxOf(rightShoreMi, leftShoreMi), inputSpeedMi)
        return Operations.unaryOperation(outputAxel, Operations.limitOperation(minMi))

    }

    companion object {

        @JvmStatic
        val RULE1: AxleRule = object : AxleRule() {
            override val rightShore: CalculatedFuzzySet = BoatSets.SHORE_TOO_CLOSE_SPEED_SLOW
            override val leftShore: CalculatedFuzzySet = BoatSets.SHORE_TOO_CLOSE_SPEED_SLOW
            override val inputSpeed: CalculatedFuzzySet = BoatSets.SPEED_SLOW
            override val outputAxel: CalculatedFuzzySet = BoatSets.AXLE_FAST
        }

    }
}