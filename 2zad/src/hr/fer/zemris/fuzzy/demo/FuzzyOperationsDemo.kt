package hr.fer.zemris.fuzzy.demo

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.Operations
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet
import hr.fer.zemris.util.Debug

/**
 * Created by bmihaela.
 */

class FuzzyOperationsDemo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val d1: IDomain = Domain.intRange(0, 11)

            val set1: IFuzzySet = MutableFuzzySet(d1)
                    .set(DomainElement.of(intArrayOf(0)), 1.0)
                    .set(DomainElement.of(intArrayOf(1)), 0.7)
                    .set(DomainElement.of(intArrayOf(2)), 0.6)
                    .set(DomainElement.of(intArrayOf(3)), 0.4)
                    .set(DomainElement.of(intArrayOf(4)), 0.2)
            Debug.print(set1, "Set1: ")

            val notSet1: IFuzzySet = Operations.unaryOperation(set1, Operations.zadehNot())
            Debug.print(notSet1, "notSet1: ")

            val union: IFuzzySet = Operations.binaryOperation(set1, notSet1, Operations.zadehOr())
            Debug.print(union, "Set1 union notSet1: ")

            val hinters: IFuzzySet = Operations.binaryOperation(set1, notSet1, Operations.hamacherTNorm(1.0))
            Debug.print(hinters, "Set1 intersection with notSet1 using parametrised Hamacher T norm with parameter 1.0:")
        }
    }
}