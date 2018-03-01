package hr.fer.zemris.fuzzy

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.DomainElement

/**
 * Created by bmihaela.
 */

interface IFuzzySet {

    fun getDomain(): IDomain

    fun getValueAt(element: DomainElement): Double
}