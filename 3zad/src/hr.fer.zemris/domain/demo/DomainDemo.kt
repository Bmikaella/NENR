package hr.fer.zemris.domain.demo

import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.util.Debug

/**
 * Created by bmihaela.
 */
class DomainDemo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val d1 = Domain.intRange(0, 5)
            Debug.print(d1, "Elementi domene d1:")

            val d2 = Domain.intRange(0, 3)
            Debug.print(d2, "Elementi domene d2:")

            val d3 = Domain.combine(d1, d2)
            Debug.print(d3, "Elementi domene d3")

            println(d3.elementForIndex(0))
            println(d3.elementForIndex(5))
            println(d3.elementForIndex(14))
            println(d3.indexOfElement(DomainElement.of(intArrayOf(4,1))))

        }
    }
}