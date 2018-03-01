package hr.fer.zemris.fuzzy

import hr.fer.zemris.fuzzy.impl.MutableFuzzySet

/**
 * Created by bmihaela.
 */

class Operations {

    companion object {
        @JvmStatic
        var zadehAND: IBinaryFunction? = null

        @JvmStatic
        var zadehOR: IBinaryFunction? = null

        @JvmStatic
        var zadehNOT: IUnaryFunction? = null

        @JvmStatic
        fun limitOperation(limitTo: Double): IUnaryFunction {
            return object : IUnaryFunction {
                override fun valueAt(value: Double): Double {
                    if (value > limitTo) {
                        return limitTo
                    }
                    return value
                }
            }
        }

        @JvmStatic
        fun unaryOperation(fuzzySet: IFuzzySet, unaryFunction: IUnaryFunction): IFuzzySet {
            val newSet = MutableFuzzySet(fuzzySet.getDomain())
            for (element in newSet.iDomain) {
                newSet.set(element, unaryFunction.valueAt(fuzzySet.getValueAt(element)))
            }
            return newSet
        }

        @JvmStatic
        fun binaryOperation(fuzzySet1: IFuzzySet, fuzzySet2: IFuzzySet, binaryFunction: IBinaryFunction): IFuzzySet {
            if (fuzzySet1.getDomain() != fuzzySet2.getDomain()) {
                throw IllegalStateException()
            }
            val newSet = MutableFuzzySet(fuzzySet1.getDomain())
            for (element in newSet.iDomain) {
                newSet.set(element, binaryFunction.valueAt(fuzzySet1.getValueAt(element), fuzzySet2.getValueAt(element)))
            }
            return newSet
        }

        @JvmStatic
        fun zadehNot(): IUnaryFunction {
            if (zadehNOT == null) {
                zadehNOT = object : IUnaryFunction {
                    override fun valueAt(value: Double): Double {
                        return 1 - value
                    }
                }
            }
            return zadehNOT!!
        }

        @JvmStatic
        fun zadehAnd(): IBinaryFunction {
            if (zadehAND == null) {
                zadehAND = object : IBinaryFunction {
                    override fun valueAt(first: Double, second: Double): Double {
                        return minOf(first, second)
                    }
                }
            }
            return zadehAND!!
        }

        @JvmStatic
        fun zadehOr(): IBinaryFunction {
            if (zadehOR == null) {
                zadehOR = object : IBinaryFunction {
                    override fun valueAt(first: Double, second: Double): Double {
                        return maxOf(first, second)
                    }
                }
            }
            return zadehOR!!
        }

        @JvmStatic
        fun hamacherTNorm(value: Double): IBinaryFunction {
            return object : IBinaryFunction {
                override fun valueAt(first: Double, second: Double): Double {
                    return (first * second) / (value + ((1 - value) * (first + second - first * second)))
                }
            }
        }

        @JvmStatic
        fun hamacherSNorm(value: Double): IBinaryFunction {
            return object : IBinaryFunction {
                override fun valueAt(first: Double, second: Double): Double {
                    return ((first + second) - (2 - value) * first * second) / (1 - (1 - value) * first * second)
                }
            }
        }
    }

}