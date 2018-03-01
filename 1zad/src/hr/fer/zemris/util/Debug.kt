package hr.fer.zemris.util

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.fuzzy.IFuzzySet

/**
 * Created by bmihaela.
 */

class
Debug {

    companion object {
        @JvmStatic
        fun print(domain: IDomain, headingText: String) {
            println(headingText)
            domain.forEach { println("Element domene je: " + it) }
            println("Kardinalitet domene je: " + domain.getCardinality())
            println()
        }

        @JvmStatic
        fun print(fuzzySet: IFuzzySet, headingText: String) {
            println(headingText)
            fuzzySet.getDomain().forEach { domainElement -> println("d(%s)=%1.6f".format(domainElement.toString(), fuzzySet.getValueAt(domainElement))) }
            println()
        }
    }
}