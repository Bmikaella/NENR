package hr.fer.zemris.fuzzy.impl

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.IFuzzySet
import hr.fer.zemris.fuzzy.IIntUnaryFunction

/**
 * Created by bmihaela.
 */

class CalculatedFuzzySet(val iDomain: IDomain, val function: IIntUnaryFunction) : IFuzzySet {


    override fun getDomain(): IDomain {
        return iDomain
    }

    override fun getValueAt(element: DomainElement): Double {
        return function.valueAt(iDomain.indexOfElement(element))
    }

}