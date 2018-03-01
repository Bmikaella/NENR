package hr.fer.zemris.fuzzy.impl

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet

/**
 * Created by bmihaela.
 */

class MutableFuzzySet(val iDomain: IDomain) : IFuzzySet {
    var membership = DoubleArray(iDomain.getCardinality())

    fun set(element: DomainElement, value: Double): MutableFuzzySet {
        membership[iDomain.indexOfElement(element)] = value
        return this
    }

    override fun getDomain(): IDomain {
        return iDomain
    }

    override fun getValueAt(element: DomainElement): Double {
        val index = iDomain.indexOfElement(element)
        if (index >= membership.size || index < 0) {
            throw IllegalArgumentException()
        }
        return membership[index]
    }
}