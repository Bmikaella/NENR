package hr.fer.zemris.domain.impl

/**
 * Created by bmihaela.
 */

class SimpleDomain(val first: Int, val last: Int) : BasicDomain() {

    override fun getCardinality(): Int {
        return last - first
    }

    override fun iterator(): Iterator<DomainElement> {
        return SimpleDomainIterator(this)
    }

    class SimpleDomainIterator(val simpleDomain: SimpleDomain) : Iterator<DomainElement> {
        var current = 0

        init {
            current = simpleDomain.first
        }

        override fun hasNext(): Boolean {
            return current < simpleDomain.last
        }

        override fun next(): DomainElement {
            if (!hasNext()) {
                throw IllegalStateException()
            }
            val element = DomainElement(intArrayOf(current))
            current++
            return element
        }

    }

}