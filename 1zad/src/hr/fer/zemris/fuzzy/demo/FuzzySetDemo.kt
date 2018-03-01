package hr.fer.zemris.fuzzy.demo

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.StandardFuzzySets
import hr.fer.zemris.fuzzy.impl.CalculatedFuzzySet
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet
import hr.fer.zemris.util.Debug

/**
 * Created by bmihaela.
 */

class FuzzySetDemo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val d1: IDomain = Domain.intRange(0, 11)

            val set1: IFuzzySet = MutableFuzzySet(d1)
                    .set(DomainElement.of(intArrayOf(0)), 1.0)
                    .set(DomainElement.of(intArrayOf(1)), 0.8)
                    .set(DomainElement.of(intArrayOf(2)), 0.6)
                    .set(DomainElement.of(intArrayOf(3)), 0.4)
                    .set(DomainElement.of(intArrayOf(4)), 0.2)

            Debug.print(set1, "Set1:")

            val d2: IDomain = Domain.intRange(-5, 6)
            val set2: IFuzzySet = CalculatedFuzzySet(d2, StandardFuzzySets.lambdaFunction(
                    d2.indexOfElement(DomainElement.of(intArrayOf(-4))),
                    d2.indexOfElement(DomainElement.of(intArrayOf(0))),
                    d2.indexOfElement(DomainElement.of(intArrayOf(4)))
            ))

            Debug.print(set2, "Set2:")

        }
    }
}