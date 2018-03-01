package hr.fer.zemris.fuzzy.demo

import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.Relations
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet


/**
 * Created by bmihaela.
 */

class EquivalentDemo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val u = Domain.intRange(1, 5) // {1,2,3,4}
            val r = MutableFuzzySet(Domain.combine(u, u))
                    .set(DomainElement.of(intArrayOf(1, 1)), 1.0)
                    .set(DomainElement.of(intArrayOf(2, 2)), 1.0)
                    .set(DomainElement.of(intArrayOf(3, 3)), 1.0)
                    .set(DomainElement.of(intArrayOf(4, 4)), 1.0)
                    .set(DomainElement.of(intArrayOf(1, 2)), 0.3)
                    .set(DomainElement.of(intArrayOf(2, 1)), 0.3)
                    .set(DomainElement.of(intArrayOf(2, 3)), 0.5)
                    .set(DomainElement.of(intArrayOf(3, 2)), 0.5)
                    .set(DomainElement.of(intArrayOf(3, 4)), 0.2)
                    .set(DomainElement.of(intArrayOf(4, 3)), 0.2)
            var r2: IFuzzySet = r
            println(
                    "Početna relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2))
            println()
            for (i in 1..3) {
                r2 = Relations.compositionOfBinaryRelations(r2, r)
                println(
                        "Broj odrađenih kompozicija: $i. Relacija je:")
                for (e in r2.getDomain()) {
                    println("mu(" + e + ")=" + r2.getValueAt(e))
                }
                println(
                        "Ova relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2))
                println()
            }
        }
    }
}