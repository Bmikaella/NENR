package hr.fer.zemris.fuzzy

/**
 * Created by bmihaela.
 */

class StandardFuzzySets {
    companion object {
        @JvmStatic
        private val MIN = 0.0
        @JvmStatic
        private val MAX = 1.0

        @JvmStatic
        fun IFunction(first: Int, second: Int): IIntUnaryFunction {
            return object : IIntUnaryFunction {
                override fun valueAt(index: Int): Double {
                    if (index < first) {
                        return MAX
                    } else if (index >= second) {
                        return MIN
                    }
                    return (second - index) / ((second - first).toDouble())
                }
            }
        }

        @JvmStatic
        fun gammaFunction(first: Int, second: Int): IIntUnaryFunction {
            return object : IIntUnaryFunction {
                override fun valueAt(index: Int): Double {
                    if (index < first) {
                        return MIN
                    } else if (index >= second) {
                        return MAX
                    }
                    return (index - first) / ((second - first).toDouble())
                }
            }
        }

        @JvmStatic
        fun lambdaFunction(first: Int, second: Int, third: Int): IIntUnaryFunction {
            return object : IIntUnaryFunction {
                override fun valueAt(index: Int): Double {
                    if (index < first) {
                        return MIN
                    } else if (index >= third) {
                        return MIN
                    }
                    if (index < second && index >= first) {
                        return (index - first) / ((second - first).toDouble())
                    } else {
                        return (third - index) / ((third - second).toDouble())
                    }
                }
            }
        }
    }
}