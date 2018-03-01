package hr.fer.zemris.database.rules

import hr.fer.zemris.boat.BoatSets
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.Operations
import hr.fer.zemris.fuzzy.impl.CalculatedFuzzySet

/**
 * Created by bmihaela.
 */
abstract class RudderRule {

    abstract val angledRightShore: CalculatedFuzzySet
    abstract val angledLeftShore: CalculatedFuzzySet
    abstract val rightShore: CalculatedFuzzySet
    abstract val leftShore: CalculatedFuzzySet
    abstract val outputRudder: CalculatedFuzzySet
    //
    open fun getFuzzySetConclusion(rightAngledDistance: Int, leftAngledDistance: Int, rightDistance: Int, leftDistance: Int): IFuzzySet {
        val rightRudderMi = angledRightShore.getValueAt(DomainElement(intArrayOf(rightAngledDistance)))
        val leftRudderMi = angledLeftShore.getValueAt(DomainElement(intArrayOf(leftAngledDistance)))
        val rightShoreMi = rightShore.getValueAt(DomainElement(intArrayOf(rightDistance)))
        val leftShoreMi = leftShore.getValueAt(DomainElement(intArrayOf(leftDistance)))
        val minMi = minOf(leftShoreMi, minOf(rightShoreMi, minOf(rightRudderMi, leftRudderMi)))
        return Operations.unaryOperation(outputRudder, Operations.limitOperation(minMi))
    }

    companion object {

        @JvmStatic
        val RULE1: RudderRule = object : RudderRule() {
            override val rightShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val leftShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val angledRightShore: CalculatedFuzzySet = BoatSets.SHORE_TOO_CLOSE
            override val angledLeftShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val outputRudder: CalculatedFuzzySet = BoatSets.RUDDER_STEEP_LEFT
        }

        @JvmStatic
        val RULE2: RudderRule = object : RudderRule() {
            override val rightShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val leftShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val angledRightShore: CalculatedFuzzySet = BoatSets.SHORE_ALL_GOES
            override val angledLeftShore: CalculatedFuzzySet = BoatSets.SHORE_TOO_CLOSE
            override val outputRudder: CalculatedFuzzySet = BoatSets.RUDDER_STEEP_RIGHT
        }
    }

}