package hr.fer.zemris.domain

import hr.fer.zemris.domain.impl.BasicDomain
import hr.fer.zemris.domain.impl.DomainElement

/**
 * Created by bmihaela.
 */


interface IDomain : Iterable<DomainElement> {

    fun getCardinality(): Int

    fun getComponent(index: Int): BasicDomain

    fun getNumberOfComponents(): Int

    fun indexOfElement(element: DomainElement): Int

    fun elementForIndex(index: Int): DomainElement?
}