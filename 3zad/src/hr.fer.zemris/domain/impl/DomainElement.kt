package hr.fer.zemris.domain.impl

import java.util.*

/**
 * Created by bmihaela.
 */

class DomainElement(val elements: IntArray) {

    fun getNumberOfComponents(): Int {
        return elements.size
    }

    fun getComponentValue(index: Int): Int {
        return elements[index]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as DomainElement

        if (!Arrays.equals(elements, other.elements)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(elements)
    }

    override fun toString(): String {
        if (elements.isEmpty()) {
            return ""
        }
        if (elements.size == 1) {
            return elements[0].toString()
        }
        val builder: StringBuilder = StringBuilder("(")
        elements.forEach { builder.append(it).append(",") }
        builder.deleteCharAt(builder.lastIndex)
        return builder.append(")").toString()
    }

    companion object {
        @JvmStatic
        fun of(elements: IntArray): DomainElement {
            return DomainElement(elements)
        }

        @JvmStatic
        fun combine(element1: DomainElement, element2: DomainElement): DomainElement {
            val allElements = mutableListOf<Int>()
            allElements.addAll(element1.elements.asList())
            allElements.addAll(element2.elements.asList())
            return DomainElement(allElements.toIntArray())
        }
    }

}