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

    override fun getMaxComponent(): Int {
        return last-1
    }

    override fun getMinComponent(): Int {
        return first
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as SimpleDomain

        if (first != other.first) return false
        if (last != other.last) return false

        return true
    }

    override fun hashCode(): Int {
        var result = first
        result = 31 * result + last
        return result
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