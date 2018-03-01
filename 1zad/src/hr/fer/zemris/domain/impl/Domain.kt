package hr.fer.zemris.domain.impl

import hr.fer.zemris.domain.IDomain

/**
 * Created by bmihaela.
 */

abstract class Domain : IDomain {

    companion object {
        @JvmStatic
        fun intRange(to: Int, from: Int): IDomain {
            return SimpleDomain(to, from)
        }

        @JvmStatic
        fun combine(domain1: IDomain, domain2: IDomain): Domain {
            val domains: MutableList<BasicDomain> = mutableListOf()
            domains.addAll(getSimpleDomains(domain1))
            domains.addAll(getSimpleDomains(domain2))
            return CompositeDomain(domains.toTypedArray())
        }

        @JvmStatic
        fun getSimpleDomains(domain: IDomain): MutableList<BasicDomain> {
            val size1 = domain.getNumberOfComponents()
            val domains: MutableList<BasicDomain> = mutableListOf()
            for (index1 in 0 until size1) {
                domains.add(domain.getComponent(index1))
            }
            return domains
        }
    }

    override fun indexOfElement(element: DomainElement): Int {
        for ((index, x) in this.withIndex()) {
            if (x.equals(element)) {
                return index
            }
        }
        return -1
    }

    override fun elementForIndex(index: Int): DomainElement? {
        for ((i, x) in this.withIndex()) {
            if (i == index) {
                return x
            }
        }
        return null
    }
}