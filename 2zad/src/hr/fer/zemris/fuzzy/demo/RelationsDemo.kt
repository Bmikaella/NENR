package hr.fer.zemris.fuzzy.demo

import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.Relations
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet



/**
 * Created by bmihaela.
 */

class RelationsDemo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val domain = Domain.intRange(1, 6)
            val domain2 = Domain.combine(domain, domain)

            val fuzzySet1 = MutableFuzzySet(domain2)
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 1.0)
                    .set(DomainElement.of(intArrayOf(4, 4)), 1.0)
                    .set(DomainElement.of(intArrayOf(5, 5)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 1)), 0.5)
                    .set(DomainElement.of(intArrayOf(1, 3)), 0.5)
            val fuzzySet2 = MutableFuzzySet(domain2)
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 1.0)
                    .set(DomainElement.of(intArrayOf(4, 4)), 1.0)
                    .set(DomainElement.of(intArrayOf(5, 5)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 1)), 0.5)
                    .set(DomainElement.of(intArrayOf(1, 3)), 0.1)

            val fuzzySet3 = MutableFuzzySet(domain2)
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 0.3)
                    .set(DomainElement.of(intArrayOf(4, 4)), 1.0)
                    .set(DomainElement.of(intArrayOf(5, 5)), 1.0)
                    .set(DomainElement.of(intArrayOf(1, 2)), 0.6)
                    .set(DomainElement.of(intArrayOf(2, 1)), 0.6)
                    .set(DomainElement.of(intArrayOf(2, 3)), 0.7)
                    .set(DomainElement.of(intArrayOf(3, 2)), 0.7)
                    .set(DomainElement.of(intArrayOf(3, 1)), 0.5)
                    .set(DomainElement.of(intArrayOf(1, 3)), 0.5)


            val fuzzySet4 = MutableFuzzySet(domain2)
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 1.0)
                    .set(DomainElement.of(intArrayOf(4, 4)), 1.0)
                    .set(DomainElement.of(intArrayOf(5, 5)), 1.0)
                    .set(DomainElement.of(intArrayOf(1, 2)), 0.4)
                    .set(DomainElement.of(intArrayOf(2, 1)), 0.4)
                    .set(DomainElement.of(intArrayOf(2, 3)), 0.5)
                    .set(DomainElement.of(intArrayOf(3, 2)), 0.5)
                    .set(DomainElement.of(intArrayOf(1, 3)), 0.4)
                    .set(DomainElement.of(intArrayOf(3, 1)), 0.4)

            val test1 = Relations.isUtimesURelation(fuzzySet1)
            println("r1 je definiran nad UxU? " + test1)
            val test2 = Relations.isSymmetric(fuzzySet1)
            println("r1 je simetrična? " + test2)
            val test3 = Relations.isSymmetric(fuzzySet2)
            println("r2 je simetrična? " + test3)
            val test4 = Relations.isReflexive(fuzzySet1)
            println("r1 je refleksivna? " + test4)
            val test5 = Relations.isReflexive(fuzzySet3)
            println("r3 je refleksivna? " + test5)
            val test6 = Relations.isMaxMinTransitive(fuzzySet3)
            println("r3 je max-min tranzitivna? " + test6)
            val test7 = Relations.isMaxMinTransitive(fuzzySet4)
            println("r4 je max-min tranzitivna? " + test7)
        }
    }
}