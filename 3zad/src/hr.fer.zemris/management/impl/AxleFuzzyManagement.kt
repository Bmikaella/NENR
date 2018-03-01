package hr.fer.zemris.management.impl

import hr.fer.zemris.database.defuzz.DeFuzzy
import hr.fer.zemris.database.rules.AxleRule
import hr.fer.zemris.management.FuzzyManagement

/**
 * Created by bmihaela.
 */
class AxleFuzzyManagement(val defuzzer: DeFuzzy) : FuzzyManagement {

    override fun getNewOutputValue(rightAngledDistance: Int, leftAngledDistance: Int, rightDistance: Int, leftDistance: Int, speed: Int, oke: Int): Int {
        val set1 = AxleRule.RULE1.getFuzzySetConclusion(rightDistance, leftDistance, speed)

        val union = set1
        val speedValue = defuzzer.deFuzzyMe(union)
        return speedValue
    }
}