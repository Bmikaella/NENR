package hr.fer.zemris.management.impl

import hr.fer.zemris.boat.BoatSets
import hr.fer.zemris.database.defuzz.DeFuzzy
import hr.fer.zemris.database.rules.RudderRule
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.Operations
import hr.fer.zemris.management.FuzzyManagement

/**
 * Created by bmihaela.
 */

class RudderFuzzyManagement(val defuzzer: DeFuzzy) : FuzzyManagement {
    override fun getNewOutputValue(rightAngledDistance: Int, leftAngledDistance: Int, rightDistance: Int, leftDistance: Int, speed: Int, oke: Int): Int {
        val set1 = RudderRule.RULE1.getFuzzySetConclusion(rightAngledDistance, leftAngledDistance, rightDistance,leftDistance)
        val set2 = RudderRule.RULE2.getFuzzySetConclusion(rightAngledDistance, leftAngledDistance, rightDistance, leftDistance)

        val union: IFuzzySet = Operations.binaryOperation(set1, set2, Operations.zadehOr())
        val rudderValue = defuzzer.deFuzzyMe(union)
        return rudderValue - BoatSets.RUDDER_OFFSET
    }
}