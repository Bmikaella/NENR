package hr.fer.zemris.database.defuzz.impl

import hr.fer.zemris.database.defuzz.DeFuzzy
import hr.fer.zemris.fuzzy.IFuzzySet

/**
 * Created by bmihaela.
 */
class Defuzzers {

    companion object {

        @JvmStatic
        val CENTER_OF_AREA: DeFuzzy = object : DeFuzzy {
            override fun deFuzzyMe(fuzzySet: IFuzzySet): Int {
                var sumLow = 0.0
                var sumUp = 0.0
                for (elem in fuzzySet.getDomain()) {
                    sumLow += fuzzySet.getValueAt(elem)
                    sumUp += fuzzySet.getValueAt(elem) * elem.elements[0]
                }
                return ((sumUp / sumLow).toInt())
            }
        }
    }

}