package hr.fer.zemris.domain.impl


/**
 * Created by bmihaela.
 */

class CompositeDomain(val domains: Array<BasicDomain>) : Domain() {

    override fun iterator(): Iterator<DomainElement> {
        return CompositeDomainIterator(this)
    }

    override fun getNumberOfComponents(): Int {
        return domains.size
    }

    override fun getComponent(index: Int): BasicDomain {
        return domains[index]
    }

    override fun getCardinality(): Int {
        var card = 1
        for (domain in domains) {
            card *= domain.getCardinality()
        }
        return card
    }

    class CompositeDomainIterator(val compositeDomain: CompositeDomain) : Iterator<DomainElement> {
        var domainSizes = mutableListOf<Int>()
        var currentIndex = mutableListOf<Int>()
        var elementsLeft = compositeDomain.getCardinality()
        var carrier = 0
        var firstCarrier = 0

        init {
            for (domain in compositeDomain.domains) {
                domainSizes.add(domain.getCardinality())
                currentIndex.add(0)
            }
        }

        override fun hasNext(): Boolean {
            return elementsLeft > 0
        }

        override fun next(): DomainElement {
            if (!hasNext()) {
                throw IllegalStateException()
            }
            carrier = 0
            var element: DomainElement? = null
            for (index in currentIndex.size - 1 downTo 0) {
                if (currentIndex[index] < domainSizes[index]) {
                    currentIndex[index] = currentIndex[index] + carrier
                    for (elemDomIndex in 0 until domainSizes.size) {
                        if (elemDomIndex == 0) {
                            element = compositeDomain.getComponent(elemDomIndex).elementForIndex(currentIndex[elemDomIndex])!!
                            continue
                        }
                        element = DomainElement.combine(element!!, compositeDomain.getComponent(elemDomIndex).elementForIndex(currentIndex[elemDomIndex])!!)
                    }
                    if (carrier == 1) {
                        currentIndex[firstCarrier] = currentIndex[firstCarrier] + 1
                    } else {
                        currentIndex[index] = currentIndex[index] + 1
                    }
                    break
                } else {
                    currentIndex[index] = 0
                    if (carrier != 1) {
                        carrier = 1
                        firstCarrier = index
                    }
                }
            }
            elementsLeft--
            return element!!
        }

    }
}