package hr.fer.zemris.fuzzy

import hr.fer.zemris.domain.IDomain
import hr.fer.zemris.domain.impl.Domain
import hr.fer.zemris.domain.impl.DomainElement
import hr.fer.zemris.fuzzy.impl.MutableFuzzySet
import hr.fer.zemris.util.Preconditions

/**
 * Created by bmihaela.
 */
class Relations {
    companion object {

        @JvmStatic
        fun isUtimesURelation(relation: IFuzzySet): Boolean {
            val domain = relation.getDomain()
            val numberOfComponents = domain.getNumberOfComponents()
            if (numberOfComponents != 2) {
                return false
            }

            if (domain.getComponent(0).getMinComponent() != domain.getComponent(1).getMinComponent()) {
                return false
            }
            if (domain.getComponent(0).getMaxComponent() != domain.getComponent(1).getMaxComponent()) {
                return false
            }
//
//            var max1 = 0
//            var max2 = 0
//            var min1 = 0
//            var min2 = 0
//
//            for (element in domain) {
//                if (element.getNumberOfComponents() != 2) {
//                    return false
//                }
//                val componentValue1 = element.getComponentValue(0)
//                val componentValue2 = element.getComponentValue(1)
//                if (componentValue1 > max1) {
//                    max1 = componentValue1
//                } else if (componentValue1 < min1) {
//                    min1 = componentValue1
//                }
//                if (componentValue2 > max2) {
//                    max2 = componentValue2
//                } else if (componentValue2 < min2) {
//                    min2 = componentValue2
//                }
//            }
//            if (min1 != min2) {
//                return false
//            }
//            if (max1 != max2) {
//                return false
//            }
            return true
        }

        @JvmStatic
        fun isSymmetric(relation: IFuzzySet): Boolean {
            Preconditions.requireRelationTo(Relations.isUtimesURelation(relation), "Relation needs to have paired elements")
            val domain = relation.getDomain()
            for (element in domain) {
                val newElement = DomainElement(intArrayOf(element.getComponentValue(1), element.getComponentValue(0)))
                if (relation.getValueAt(newElement).compareTo(relation.getValueAt(element)) != 0) {
                    return false
                }
            }
            return true
        }

        @JvmStatic
        fun isReflexive(relation: IFuzzySet): Boolean {
            Preconditions.requireRelationTo(Relations.isUtimesURelation(relation), "Relation needs to have paired elements")
            val domain = relation.getDomain()
            for (element in domain) {
                if (element.getComponentValue(0) == element.getComponentValue(1)) {
                    if (relation.getValueAt(element).compareTo(1.0) != 0) {
                        return false
                    }
                }
            }
            return true
        }

        @JvmStatic
        fun isMaxMinTransitive(relation: IFuzzySet): Boolean {
            Preconditions.requireRelationTo(Relations.isUtimesURelation(relation), "Relation needs to have paired elements")
            val domain = relation.getDomain()
            for (element in domain) {
                val transitionTestValue = relation.getValueAt(element)

                val endRange = domain.getComponent(0).getMaxComponent()
                val beginRange = domain.getComponent(0).getMinComponent()
                val resultValue = getCompositionValueOfElement(domain, element, relation, relation)
//                for (x in beginRange..endRange) {
//                    val firstElement = DomainElement(intArrayOf(element.getComponentValue(0), x))
//                    val secondElement = DomainElement(intArrayOf(x, element.getComponentValue(1)))
//
//                    resultValue = maxOf(minOf(relation.getValueAt(firstElement), relation.getValueAt(secondElement)), resultValue)
//                }
                if (transitionTestValue < resultValue) {
                    return false
                }
            }
            return true
        }

        @JvmStatic
        private fun getCompositionValueOfElement(domain: IDomain, element: DomainElement, relation1: IFuzzySet, relation2: IFuzzySet): Double {
            val endRange = domain.getComponent(0).getMaxComponent()
            val beginRange = domain.getComponent(0).getMinComponent()
            var resultValue = 0.0
            for (x in beginRange..endRange) {
                val firstElement = DomainElement(intArrayOf(element.getComponentValue(0), x))
                val secondElement = DomainElement(intArrayOf(x, element.getComponentValue(1)))

                resultValue = maxOf(minOf(relation1.getValueAt(firstElement), relation2.getValueAt(secondElement)), resultValue)
            }
            return resultValue
        }

        @JvmStatic
        fun compositionOfBinaryRelations(relation1: IFuzzySet, relation2: IFuzzySet): IFuzzySet {
            Preconditions.requireRelationTo(relation1.getDomain().getComponent(1) == relation2.getDomain().getComponent(0)
                    , "There can't be composition of relations cause " +
                    "their domains don't fit domain composition criteria")

            val domain1 = relation1.getDomain()
            val domain2 = relation2.getDomain()
            val first = domain1.getComponent(0).getMinComponent()
            val offsetFirst = domain1.getComponent(0).getCardinality()
            val second = domain2.getComponent(1).getMinComponent()
            val offsetSecond = domain2.getComponent(1).getCardinality()
            val newFirstDomain = Domain.intRange(first, first + offsetFirst)
            val newSecondDomain = Domain.intRange(second, second + offsetSecond)
            val newSet = MutableFuzzySet(Domain.combine(newFirstDomain, newSecondDomain))
            for (element in newSet.iDomain) {
                val resultValue = getCompositionValueOfElement(domain2, element, relation1, relation2)
                newSet.set(element, resultValue)
            }
            return newSet
        }

        @JvmStatic
        fun isFuzzyEquivalence(relation: IFuzzySet): Boolean {
            if(Relations.isMaxMinTransitive(relation) && Relations.isReflexive(relation) && Relations.isSymmetric(relation)){
                return true
            }
            return false
        }
    }
}