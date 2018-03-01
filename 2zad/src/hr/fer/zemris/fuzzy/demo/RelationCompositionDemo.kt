package hr.fer.zemris.fuzzy.demo

import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.Relations
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet


/**
 * Created by bmihaela.
 */

class RelationCompositionDemo {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val u1 = Domain.intRange(1, 5) // {1,2,3,4}
            val u2 = Domain.intRange(1, 4) // {1,2,3}
            val u3 = Domain.intRange(1, 5) // {1,2,3,4}
            val r1 = MutableFuzzySet(Domain.combine(u1, u2))
                    .set(DomainElement.of(intArrayOf(1, 1)), 0.3)
                    .set(DomainElement.of(intArrayOf(1, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 0.5)
                    .set(DomainElement.of(intArrayOf(4, 3)), 0.5)
            val r2 = MutableFuzzySet(Domain.combine(u2, u3))
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 1)), 0.5)
                    .set(DomainElement.of(intArrayOf(2, 2)), 0.7)
                    .set(DomainElement.of(intArrayOf(3, 3)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 4)), 0.4)
            val r1r2 = Relations.compositionOfBinaryRelations(r1, r2)
            for (e in r1r2.getDomain()) {
                println("mu(" + e + ")=" + r1r2.getValueAt(e))
            }
        }
    }
}